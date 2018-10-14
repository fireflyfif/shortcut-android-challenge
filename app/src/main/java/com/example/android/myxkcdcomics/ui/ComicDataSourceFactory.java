package com.example.android.myxkcdcomics.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.android.myxkcdcomics.model.CurrentXkcdComic;


public class ComicDataSourceFactory extends DataSource.Factory<Long, CurrentXkcdComic> {

    private MutableLiveData<ComicDataSource> comicLiveData;
    private ComicDataSource comicDataSource;

    public ComicDataSourceFactory() {
        comicLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Long, CurrentXkcdComic> create() {
        comicDataSource = new ComicDataSource();
        comicLiveData.postValue(comicDataSource);

        return comicDataSource;
    }

    public MutableLiveData<ComicDataSource> getComicLiveData() {
        return comicLiveData;
    }
}
