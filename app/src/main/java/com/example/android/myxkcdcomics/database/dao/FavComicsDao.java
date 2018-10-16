package com.example.android.myxkcdcomics.database.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.myxkcdcomics.database.FavComic;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;

import java.util.List;


@Dao
public interface FavComicsDao {

    @Insert
    void insertComic(FavComic favComic);

    @Query("SELECT * FROM fav_comics")
    List<FavComic> allComics();

    @Query("SELECT * FROM fav_comics")
    DataSource.Factory<Integer, FavComic> getAllFavComics();

    @Query("SELECT number FROM fav_comics WHERE number = :comicNum")
    String getComicByNum(String comicNum);

    @Query("DELETE FROM fav_comics WHERE number = :comicNumber")
    void deleteComic(String comicNumber);

    @Query("DELETE FROM fav_comics")
    void deleteAllData();
}