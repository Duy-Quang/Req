package com.example.req.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface QAContentDao {
    @Query("SELECT * FROM " + QAContentDatabase.TABLE_NAME + " ORDER BY ")
    QAContent[] getAllQAContent();

    @Query("SELECT * FROM " + QAContentDatabase.TABLE_NAME + " ORDER BY RANDOM() LIMIT :questionPoolAmount")
    void getRandomQAContet(int questionPoolAmount);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQAContent(QAContent... qaContents);

    @Query("DELETE FROM QAContent")
    void deleteAll();

    @Update
    void updateUser(QAContent... qaContents);
}
