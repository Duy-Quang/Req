package com.example.req;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button mStartButton;

//    private QuestionDataBase[] questionDataBase = new QuestionDataBase[Global.QUESTION_DB_AMOUNT];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartButton = (Button) findViewById(R.id.b_start);

        //generate random 30 question DB
        QuestionDataBase[] questionDataBase = new QuestionDataBase[Global.QUESTION_DB_AMOUNT];
        for(int i = 0; i < Global.QUESTION_DB_AMOUNT ; i++) {
            questionDataBase[i] = getRandomContent(i);
        }
        Global.setQuestionDataBase(questionDataBase);

        //get random 10 question of DB
        int[] abstractQuestionPool = getRandomContentOfDB(Global.QUESTION_POOL_AMMOUT,questionDataBase);
        Global.setAbstractQuestionPool(abstractQuestionPool);

//        for(int i = 0; i < QUESTION_POOL_AMMOUT; i++) {
//            questionPool[i] = questionDataBase[questionPoolIndexs[i] - 1];
//        }

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });

        int debug = 0;
    }

    private QuestionDataBase getRandomContent(int position){
        QuestionDataBase content = new QuestionDataBase();
        content.setQuestion("Question content " + Integer.toString(position +1));
        content.setAnswers(new String[] {Integer.toString(new Random().nextInt(1000)),
                                            Integer.toString(new Random().nextInt(1000)),
                                            Integer.toString(new Random().nextInt(1000)),
                                            Integer.toString(new Random().nextInt(1000))});
        content.setCorrect_answer((new Random().nextInt(4))+1);

        return content;

    }

    private int[] getRandomContentOfDB(int numberOfElements,QuestionDataBase[] dataBase) {
        int arrayLength = dataBase.length;
        ArrayList<Integer> abstractArray = new ArrayList<Integer>();
        int[] result = new int[numberOfElements];

        for (int i = 0; i < arrayLength; i++) {
            abstractArray.add(i+1);
        }

        for(int i =0;i < numberOfElements;i++){
            int randomIndex = new Random().nextInt(abstractArray.size());
            result[i] = abstractArray.get(randomIndex);
            abstractArray.remove(randomIndex);
        }
        return result;
    }
}
