package com.example.req.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class QAContract {

    public static final String CONTENT_AUTHORITY = "com.example.req";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_QA = "QA";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_QA);



    private QAContract(){}

    public static final class QAEntry implements BaseColumns{
        public static final String DATABASE_NAME = "shelter.db";
        public static final String TABLE_NAME = "QAContent";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER_1 = "answer_1";
        public static final String COLUMN_ANSWER_2 = "answer_2";
        public static final String COLUMN_ANSWER_3 = "answer_3";
        public static final String COLUMN_ANSWER_4 = "answer_4";
        public static final String COLUMN_CORRECT_ANSWER = "correct_answer";

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/"
                + PATH_QA;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/"
                + PATH_QA;

    }
}
