package com.example.req;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.example.req.data.QAContent;
import com.example.req.data.QAContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.req.data.QAContract.QAEntry;
public class TestActivity extends AppCompatActivity implements ConfirmDialog.ConfirmDialogOnClickListener,LoaderManager.LoaderCallbacks<Cursor> {

    public static final String QUESTION_NUMBER = "QUESTION_NUMBER";
    public static final String QUESTION_CONTENT = "QUESTION_CONTENT";
    public static final String ANSWERS = "ANSWERS";
    public static final String CORRECT_ANSWER = "CORRECT_ANSWER";

    private static final String QA_INDEX = "QA_INDEX";
    private static final int QA_LOADER_ID = 30;

    private int questionIndex ;
    private int correctAnswerCount;
    private QAContent qaContent;

    private Cursor dataCursor;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //get data from calling intent
        Intent thisIntent = getIntent();
        questionIndex = thisIntent.getIntExtra(MainActivity.QUESTION_INDEX,0);
        correctAnswerCount = thisIntent.getIntExtra(MainActivity.CORRECT_ANSWER_COUNT,0);

        fragmentManager = getSupportFragmentManager();

        qaContent = new QAContent();

        //begin to load from database
        Bundle args = new Bundle();
        args.putInt(QA_INDEX,questionIndex);
        getLoaderManager().initLoader(this.QA_LOADER_ID,args,  this);
    }



    private QAContent getDataFromCursor(int position) {
        QAContent qaContent = new QAContent();

        String questionData;
        String[] answerData = new String[4];
        final int correctAnswerData;

        dataCursor.moveToPosition(position);
        questionData = dataCursor.getString(dataCursor.getColumnIndex(QAEntry.COLUMN_QUESTION));
        answerData[0]= dataCursor.getString(dataCursor.getColumnIndex(QAEntry.COLUMN_ANSWER_1));
        answerData[1]= dataCursor.getString(dataCursor.getColumnIndex(QAEntry.COLUMN_ANSWER_2));
        answerData[2]= dataCursor.getString(dataCursor.getColumnIndex(QAEntry.COLUMN_ANSWER_3));
        answerData[3]= dataCursor.getString(dataCursor.getColumnIndex(QAEntry.COLUMN_ANSWER_4));
        correctAnswerData = dataCursor.getInt(dataCursor.getColumnIndex(QAEntry.COLUMN_CORRECT_ANSWER));


        qaContent.setQuestion(questionData);
        qaContent.setAnswers(answerData);
        qaContent.setCorrect_answer(correctAnswerData);

        return qaContent;

    }

    @Override
    public void onBackPressed() {
        return;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projecttion = {
                QAEntry._ID,
                QAEntry.COLUMN_QUESTION,
                QAEntry.COLUMN_ANSWER_1,
                QAEntry.COLUMN_ANSWER_2,
                QAEntry.COLUMN_ANSWER_3,
                QAEntry.COLUMN_ANSWER_4,
                QAEntry.COLUMN_CORRECT_ANSWER
        };

        int index = args.getInt(QA_INDEX);

        return new CursorLoader(this,
                Uri.withAppendedPath(QAContract.CONTENT_URI,"random"),
                projecttion,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dataCursor = data;
        //get first QA content
        qaContent = getDataFromCursor(questionIndex - 1);

        //begin first fragment
        Bundle fragmentData = new Bundle();
        fragmentData.putString(QUESTION_CONTENT,qaContent.getQuestion());
        fragmentData.putStringArray(ANSWERS,qaContent.getAnswers());
        fragmentData.putInt(CORRECT_ANSWER,qaContent.getCorrect_answer());
        fragmentData.putInt(QUESTION_NUMBER,questionIndex);

        TestFragment testFragment = TestFragment.newInstance(fragmentData);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.enter_from_left,0)
                .add(R.id.fragment_test_container,testFragment)
                .commit();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //
    }

    public void increaseCorrectAnswerCount(){
        correctAnswerCount++;
    }


    @Override
    public void onConfirmClick() {
        int questionPoolAmount = getResources().getInteger(R.integer.QUESTION_POOL_AMMOUT);
        if(questionIndex <questionPoolAmount){
            //increase question index
            questionIndex++;

            //get next QA content
            qaContent = getDataFromCursor(questionIndex-1);

            //start new fragment to replace previous one
            Bundle fragmentData = new Bundle();
            fragmentData.putString(QUESTION_CONTENT,qaContent.getQuestion());
            fragmentData.putStringArray(ANSWERS,qaContent.getAnswers());
            fragmentData.putInt(CORRECT_ANSWER,qaContent.getCorrect_answer());
            fragmentData.putInt(QUESTION_NUMBER,questionIndex);

            TestFragment testFragment = TestFragment.newInstance(fragmentData);
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.enter_from_left,0)
                    .replace(R.id.fragment_test_container,testFragment)
                    .commit();
        }
        else { //finished all question
            dataCursor.close();

            //move to result activity
            Intent intent = new Intent(TestActivity.this,ResultActivity.class);
            intent.putExtra(MainActivity.CORRECT_ANSWER_COUNT,correctAnswerCount);


            TestActivity.this.startActivity(intent);
        }
    }
}
