package com.example.limmoor.einkaufen;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM shopping")
    LiveData<List<Article>> getAllArticles();

    @Insert
    void insert(Article article);

    @Delete
    void delete(Article article);

    @Query("DELETE FROM shopping")
    void deleteAll();
}
