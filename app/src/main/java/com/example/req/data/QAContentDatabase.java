package com.example.req.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {QAContent.class},version = QAContentDatabase.DATABASE_VERSION)
public abstract class QAContentDatabase extends RoomDatabase {
    public static final String TABLE_NAME = "QAContent";
    public static final int DATABASE_VERSION = 1;

    private  static QAContentDatabase INSTANCE;
    public abstract  QAContentDao qaContentDao();

    public static QAContentDatabase getInstance(Context context){
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,QAContentDatabase.class,QAContentDatabase.TABLE_NAME)
                    .build();
        }
        return INSTANCE;
    }
}
