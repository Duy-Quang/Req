package com.example.req;

public final class Global {
    public static final int QUESTION_DB_AMOUNT = 30;
    public static final int QUESTION_POOL_AMMOUT = 10;
    private static int correctAnswerCount = 0;
    private static int questionIndex = 0;
    private static int[] abstractQuestionPool = new int[QUESTION_POOL_AMMOUT];
    private static QuestionDataBase[] questionDataBase = new QuestionDataBase[Global.QUESTION_DB_AMOUNT];

    public static int getCorrectAnswerCount(){
        return correctAnswerCount;
    }

    public static void increaseCorrectAnswerCount(){
        correctAnswerCount++;
    }

    public static int getQuestionIndex(){
        return questionIndex;
    }

    public static void increaseQuestionIndex(){
        questionIndex++;
    }

    public static void setAbstractQuestionPool(int [] data){
        abstractQuestionPool =data;
    }
    
    public static int getQuestionPoolIndex(int position) {
        return abstractQuestionPool[position];
    }

    public static void setQuestionDataBase(QuestionDataBase[] data) {
        questionDataBase = data;
    }

    public static QuestionDataBase getQuestionContent(int position){
        return questionDataBase[position];
    }

    public static void resetAll(){
        correctAnswerCount = 0;
        questionIndex = 0;

    }
}
