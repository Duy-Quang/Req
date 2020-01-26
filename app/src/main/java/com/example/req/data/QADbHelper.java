package com.example.req.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.req.data.QAContract.QAEntry;

public class QADbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public QADbHelper(Context context) {
        super(context,QAEntry.DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_QA_TABLE = "CREATE TABLE " + QAEntry.TABLE_NAME +" ("
                +QAEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +QAEntry.COLUMN_QUESTION + " TEXT NOT NULL, "
                +QAEntry.COLUMN_ANSWER_1 + " TEXT, "
                +QAEntry.COLUMN_ANSWER_2 + " TEXT, "
                +QAEntry.COLUMN_ANSWER_3 + " TEXT, "
                +QAEntry.COLUMN_ANSWER_4 + " TEXT, "
                +QAEntry.COLUMN_CORRECT_ANSWER + " INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE_QA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
