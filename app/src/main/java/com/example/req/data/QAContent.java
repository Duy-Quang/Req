package com.example.req.data;

import java.util.Random;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = QAContentDatabase.TABLE_NAME)
public class QAContent {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "questionId")
    private int mId;

    private String question;
    private String answer_1;
    private String answer_2;
    private String answer_3;
    private String answer_4;
    private int correct_answer;

//    @Ignore
//    public QAContent()

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return new String[]{answer_1,answer_2,answer_3,answer_4};
    }

    public void setAnswers(String[] answers) {
        answer_1 = answers[0];
        answer_2 = answers[1];
        answer_3 = answers[2];
        answer_4 = answers[3];

    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }

    public QAContent(){}



}
