package com.example.req;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {
    private TextView mQuestionNumber;
    private TextView mQuestionContent;
    private RadioButton mAnswer1;
    private RadioButton mAnswer2;
    private RadioButton mAnswer3;
    private RadioButton mAnswer4;
    private RadioGroup mAnswerGroup;
    private Button mOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mQuestionNumber = (TextView) findViewById(R.id.tv_question_number);
        mQuestionContent = (TextView) findViewById(R.id.tv_question_content);
        mAnswer1 = (RadioButton) findViewById(R.id.rb_answer1);
        mAnswer2 = (RadioButton) findViewById(R.id.rb_answer2);
        mAnswer3 = (RadioButton) findViewById(R.id.rb_answer3);
        mAnswer4 = (RadioButton) findViewById(R.id.rb_answer4);
        mAnswerGroup = (RadioGroup) findViewById(R.id.rg_answers_pool);
        mOk = (Button) findViewById(R.id.b_ok);

        final int questionIndex = Global.getQuestionIndex();
        mQuestionNumber.setText(Integer.toString(questionIndex + 1) + "/" );
        Global.increaseQuestionIndex();

        final int questionPoolIndex = Global.getQuestionPoolIndex( questionIndex);
        final QuestionDataBase content = Global.getQuestionContent(questionPoolIndex);

        mQuestionContent.setText(content.getQuestion());

        String[] answer = content.getAnswers();
        mAnswer1.setText(answer[0]);
        mAnswer2.setText(answer[1]);
        mAnswer3.setText(answer[2]);
        mAnswer4.setText(answer[3]);


//        String questionConetnt = Global
//        mQuestionContent.setText();
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedAnswer = mAnswerGroup.getCheckedRadioButtonId();
                if(selectedAnswer>0){
                    switch (selectedAnswer){
                        case R.id.rb_answer1: selectedAnswer = 1;
                        case R.id.rb_answer2: selectedAnswer = 2;
                        case R.id.rb_answer3: selectedAnswer = 3;
                        case R.id.rb_answer4: selectedAnswer = 4;
                    }

                    String dialogMessage;
                    if(selectedAnswer == content.getCorrect_answer()) {
                        Global.increaseCorrectAnswerCount();
                        dialogMessage = "OK";
                    }else{
                        dialogMessage= "NG";
                    }

                    new AlertDialog.Builder(TestActivity.this).setMessage(dialogMessage)
                            .setCancelable(false)
                            .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(questionIndex + 1 <Global.QUESTION_POOL_AMMOUT){
                                        Intent intent = new Intent(TestActivity.this,TestActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        TestActivity.this.startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(TestActivity.this,ResultActivity.class);
                                        TestActivity.this.startActivity(intent);
//                                        Toast.makeText(TestActivity.this,Integer.toString(correctAnswerCount),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).create().show();

                }
                else{
                    Toast.makeText(TestActivity.this,"No answer selected",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(TestActivity.this,"You can't go back, Dog!",Toast.LENGTH_SHORT).show();
        return;
    }
}
