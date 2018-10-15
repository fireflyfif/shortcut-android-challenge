package com.example.android.myxkcdcomics.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.myxkcdcomics.model.CurrentXkcdComic;

import java.util.List;

@Dao
public interface FavComicsDao {

    @Insert
    void insertComic(CurrentXkcdComic favComic);

    @Query("SELECT * FROM fav_comics")
    List<CurrentXkcdComic> allComics();

    @Query("SELECT number FROM fav_comics WHERE number = :comicNum")
    String getComicByNum(Integer comicNum);


}