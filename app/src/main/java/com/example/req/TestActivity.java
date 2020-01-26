package com.example.req;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.req.data.QAContent;
import com.example.req.data.QAContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.req.data.QAContract.QAEntry;
public class TestActivity extends AppCompatActivity implements ConfirmDialog.ConfirmDialogOnClickListener {
    private TextView mQuestionNumber;
    private TextView mQuestionContent;
    private RecyclerView mRecyclerViewAnswer;
    private AnswerAdapter mAnswerAdapter;
    private Button mOk;

    private int questionIndex ;
    private int[] abstractQuestionPool;
    private int correctAnswerCount;
    private QAContent qaContent;
    private int selectedAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mQuestionNumber = (TextView) findViewById(R.id.tv_question_number);
        mQuestionContent = (TextView) findViewById(R.id.tv_question_content);
        mRecyclerViewAnswer = (RecyclerView) findViewById(R.id.recyclerview_answer);
        mOk = (Button) findViewById(R.id.b_ok);

        //get data from calling intent
        Intent thisIntent = getIntent();
        questionIndex = thisIntent.getIntExtra(MainActivity.QUESTION_INDEX,0);
        abstractQuestionPool = thisIntent.getIntArrayExtra(MainActivity.ABSTRACT_QUESTION_POOL);
        correctAnswerCount = thisIntent.getIntExtra(MainActivity.CORRECT_ANSWER_COUNT,0);

        mQuestionNumber.setText(Integer.toString(questionIndex) + "/" );

        //get random question from database
        int randomQuestionFromDatabase = abstractQuestionPool[questionIndex-1];
        qaContent = getData(randomQuestionFromDatabase);

        //set question
        mQuestionContent.setText(qaContent.getQuestion());

        //set Answer layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mRecyclerViewAnswer.setLayoutManager(layoutManager);
        mRecyclerViewAnswer.setHasFixedSize(true);

        mAnswerAdapter = new AnswerAdapter();
        mAnswerAdapter.setAnswerData(qaContent.getAnswers());
        boolean [] tickMarkList = {false,false,false,false};
        mAnswerAdapter.setTickMarkList(tickMarkList);

        mRecyclerViewAnswer.setAdapter(mAnswerAdapter);

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedAnswer = mAnswerAdapter.getSelectedAnswer() + 1;
                if(selectedAnswer>0){

                    //check answer
                    String dialogMessage;
                    if(selectedAnswer == qaContent.getCorrect_answer()) {
                        dialogMessage = "OK";
                    }else{
                        dialogMessage= "NG";
                    }

                    FragmentManager fm = getSupportFragmentManager();
                    ConfirmDialog confirmDialog = ConfirmDialog.newInstance(dialogMessage,TestActivity.this);
                    confirmDialog.show(fm,null);

                }
                else{
                    Toast.makeText(TestActivity.this,"No answer selected",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConfirmClick() {
        if(questionIndex <Global.QUESTION_POOL_AMMOUT){
            Intent intent = new Intent(TestActivity.this,TestActivity.class);

            intent.putExtra(MainActivity.QUESTION_INDEX,questionIndex+1);
            intent.putExtra(MainActivity.ABSTRACT_QUESTION_POOL,abstractQuestionPool);
            if(selectedAnswer == qaContent.getCorrect_answer()) {
                intent.putExtra(MainActivity.CORRECT_ANSWER_COUNT,correctAnswerCount + 1);
            }else{
                intent.putExtra(MainActivity.CORRECT_ANSWER_COUNT,correctAnswerCount);
            }

            TestActivity.this.startActivity(intent);
        }
        else {
            Intent intent = new Intent(TestActivity.this,ResultActivity.class);

            if(selectedAnswer == qaContent.getCorrect_answer()) {
                intent.putExtra(MainActivity.CORRECT_ANSWER_COUNT,correctAnswerCount + 1);
            }else{
                intent.putExtra(MainActivity.CORRECT_ANSWER_COUNT,correctAnswerCount);
            }

            TestActivity.this.startActivity(intent);
        }
    }

    private QAContent getData(int index) {
        QAContent qaContent = new QAContent();

        String[] projecttion = {
                QAEntry._ID,
                QAEntry.COLUMN_QUESTION,
                QAEntry.COLUMN_ANSWER_1,
                QAEntry.COLUMN_ANSWER_2,
                QAEntry.COLUMN_ANSWER_3,
                QAEntry.COLUMN_ANSWER_4,
                QAEntry.COLUMN_CORRECT_ANSWER
        };

        Cursor cursor = getContentResolver().query(
                ContentUris.withAppendedId(QAContract.CONTENT_URI,index),
                projecttion,
                null,
                null,
                null);
        String questionData;
        String[] answerData = new String[4];
        final int correctAnswerData;
        try {
            cursor.moveToNext();
            questionData = cursor.getString(cursor.getColumnIndex(QAEntry.COLUMN_QUESTION));
            answerData[0]= cursor.getString(cursor.getColumnIndex(QAEntry.COLUMN_ANSWER_1));
            answerData[1]= cursor.getString(cursor.getColumnIndex(QAEntry.COLUMN_ANSWER_2));
            answerData[2]= cursor.getString(cursor.getColumnIndex(QAEntry.COLUMN_ANSWER_3));
            answerData[3]= cursor.getString(cursor.getColumnIndex(QAEntry.COLUMN_ANSWER_4));
            correctAnswerData = cursor.getInt(cursor.getColumnIndex(QAEntry.COLUMN_CORRECT_ANSWER));

        } finally {
            cursor.close();
        }
        qaContent.setQuestion(questionData);
        qaContent.setAnswers(answerData);
        qaContent.setCorrect_answer(correctAnswerData);

        return qaContent;
    }



    @Override
    public void onBackPressed() {
        return;
    }
}
