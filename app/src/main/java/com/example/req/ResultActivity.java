package com.example.req;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView mResult;
    private Button mDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mResult = (TextView) findViewById(R.id.tv_result);
        mDone = (Button) findViewById( R.id.b_done);

        Intent thisIntent = getIntent();
        final int correctAnswerCount = thisIntent.getIntExtra(MainActivity.CORRECT_ANSWER_COUNT,0);
        mResult.setText("Result: " + Integer.toString(correctAnswerCount) + " / " + Integer.toString(Global.QUESTION_POOL_AMMOUT));

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ResultActivity.this.startActivity(intent);
            }
        });
    }
}
