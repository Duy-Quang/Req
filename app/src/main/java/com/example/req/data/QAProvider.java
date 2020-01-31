package com.example.req.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.req.MainActivity;
import com.example.req.R;
import com.example.req.data.QAContract.QAEntry;

public class QAProvider extends ContentProvider {
    public static final String LOG_TAG = QADbHelper.class.getSimpleName();

    private QADbHelper mDbHelper;

    private static final int QA = 100;
    private static final int QA_ID = 101;
    private static final int QA_RANDOM = 999;

    private static final UriMatcher sUriMatcher = new UriMatcher((UriMatcher.NO_MATCH));

    static {
        sUriMatcher.addURI(QAContract.CONTENT_AUTHORITY,QAContract.PATH_QA,QA);
        sUriMatcher.addURI(QAContract.CONTENT_AUTHORITY,QAContract.PATH_QA + "/#",QA_ID);
        sUriMatcher.addURI(QAContract.CONTENT_AUTHORITY,QAContract.PATH_QA + "/random",QA_RANDOM);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new QADbHelper(getContext());
        return true;
    }

     
    @Override
    public Cursor query( Uri uri,   String[] projection,   String selection,   String[] selectionArgs,   String sortOrder) {
//        return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case QA:
                cursor = database.query(QAEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case QA_ID:
                selection = QAEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(QAEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case QA_RANDOM:
                int questionPoolAmount = getContext().getResources().getInteger(R.integer.QUESTION_POOL_AMMOUT);
                cursor = database.rawQuery("SELECT * FROM " + QAEntry.TABLE_NAME + " ORDER BY RANDOM() LIMIT " + questionPoolAmount, null);
                break;
            default:
                throw new IllegalArgumentException("unknown URI " + uri);
        }

        return cursor;
    }

     
    @Override
    public Uri insert( Uri uri,   ContentValues values) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match){
            case QA:
                long id = database.insert(QAEntry.TABLE_NAME,null,values);
                if(id == -1) {
                    return null;
                }
                return ContentUris.withAppendedId(uri,id);
            default:
                throw new IllegalArgumentException("unknown URI " + uri);


        }
    }

    @Override
    public int update( Uri uri,   ContentValues values,   String selection,   String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case QA:
                return database.update(QAEntry.TABLE_NAME,values,selection,selectionArgs);
            case QA_ID:
                selection = QAEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return database.update(QAEntry.TABLE_NAME,values,selection,selectionArgs);

            default:
                throw new IllegalArgumentException("unknown URI " + uri);
        }
    }

    @Override
    public int delete( Uri uri,   String selection,   String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case QA:
                return database.delete(QAEntry.TABLE_NAME,selection,selectionArgs);
            case QA_ID:
                selection = QAEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return database.delete(QAEntry.TABLE_NAME,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("unknown URI " + uri);
        }


    }

     
    @Override
    public String getType( Uri uri) {
        return null;
    }
}
