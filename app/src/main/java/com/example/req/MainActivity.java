package com.example.req;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.req.data.QAContract;
import com.example.req.data.QADbHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.example.req.data.QAContract.QAEntry;

public class MainActivity extends AppCompatActivity {
    public static final String ABSTRACT_QUESTION_POOL= "ABSTRACT_QUESTION_POOL";
    public static final String QUESTION_INDEX = "QUESTION_INDEX";
    public static final String CORRECT_ANSWER_COUNT = "CORRECT_ANSWER_COUNT";
    private Button mStartButton;
    private QADbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartButton = (Button) findViewById(R.id.b_start);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                //get random 10 question of DB
                int[] abstractQuestionPool = getRandomContentOfDB(Global.QUESTION_POOL_AMMOUT,Global.QUESTION_DB_AMOUNT);
                intent.putExtra(ABSTRACT_QUESTION_POOL,abstractQuestionPool);

                //starting index at 1
                int questionIndex = 1;
                intent.putExtra(QUESTION_INDEX,questionIndex);

                int correctAnswerCount = 0;
                intent.putExtra(CORRECT_ANSWER_COUNT,correctAnswerCount);

                MainActivity.this.startActivity(intent);

            }
        });

        //for debug
        mDbHelper = new QADbHelper(this);
        int debug = 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        File dbFile = getDatabasePath(QAEntry.DATABASE_NAME);
        if(!dbFile.exists()){
            //generate random 30 question DB
            for(int i = 0; i < Global.QUESTION_DB_AMOUNT ; i++) {
                insertRandomContent(i+1);
            }
        }
    }

    private void insertRandomContent(int position){
        ContentValues values= new ContentValues();
        values.put(QAEntry.COLUMN_QUESTION,"Question content " + String.valueOf(position));
        values.put(QAEntry.COLUMN_ANSWER_1,"answer"+Integer.toString(new Random().nextInt(1000)));
        values.put(QAEntry.COLUMN_ANSWER_2,"answer"+Integer.toString(new Random().nextInt(1000)));
        values.put(QAEntry.COLUMN_ANSWER_3,"answer"+Integer.toString(new Random().nextInt(1000)));
        values.put(QAEntry.COLUMN_ANSWER_4,"answer"+Integer.toString(new Random().nextInt(1000)));
        values.put(QAEntry.COLUMN_CORRECT_ANSWER,(new Random().nextInt(4))+1);

        Uri uri = getContentResolver().insert(QAContract.CONTENT_URI,values);

    }

    private int[] getRandomContentOfDB(int numberOfQuestion,int databaseLength) {
        ArrayList<Integer> abstractArray = new ArrayList<Integer>();
        int[] result = new int[numberOfQuestion];

        for (int i = 0; i < databaseLength; i++) {
            abstractArray.add(i+1);
        }

        for(int i =0;i < numberOfQuestion;i++){
            int randomIndex = new Random().nextInt(abstractArray.size());
            result[i] = abstractArray.get(randomIndex);
            abstractArray.remove(randomIndex);
        }
        return result;
    }
}
