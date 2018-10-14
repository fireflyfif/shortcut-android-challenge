package com.example.android.myapplication.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.android.myapplication.XkcdApplication;
import com.example.android.myapplication.model.CurrentXkcdComic;


public class ComicDataSourceFactory extends DataSource.Factory<Long, CurrentXkcdComic> {

    private MutableLiveData<ComicDataSource> comicLiveData;
    private ComicDataSource comicDataSource;
    private XkcdApplication appController;

    public ComicDataSourceFactory(XkcdApplication appController) {
        this. appController = appController;
        comicLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Long, CurrentXkcdComic> create() {
        comicDataSource = new ComicDataSource(appController);
        comicLiveData.postValue(comicDataSource);

        return comicDataSource;
    }

    public MutableLiveData<ComicDataSource> getComicLiveData() {
        return comicLiveData;
    }
}
