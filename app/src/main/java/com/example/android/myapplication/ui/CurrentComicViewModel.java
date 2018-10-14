package com.example.android.myapplication.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.example.android.myapplication.AppExecutors;
import com.example.android.myapplication.XkcdApplication;
import com.example.android.myapplication.model.CurrentXkcdComic;

public class CurrentComicViewModel extends AndroidViewModel {

    private static final String TAG = CurrentComicViewModel.class.getSimpleName();

    private LiveData<PagedList<CurrentXkcdComic>> comicsData;
    private ComicDataSourceFactory comicDataSourceFactory;

    //private LiveData<CurrentXkcdComic> comicData;

    public CurrentComicViewModel(Application application) {
        super(application);
        initCurrentComic(application);
    }

    private void initCurrentComic(Application application) {
        // Get an instance of the DataSourceFactory
        comicDataSourceFactory = new ComicDataSourceFactory((XkcdApplication) application);

        // Configure thePagedList.Config
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(2)
                .setPrefetchDistance(1)
                .setPageSize(2)
                .build();
        Log.d(TAG, "PagedList config: " + pagedListConfig.pageSize);

        comicsData = new LivePagedListBuilder<>(comicDataSourceFactory, pagedListConfig)
                .setFetchExecutor(AppExecutors.getInstance().networkIO())
                .build();
    }

    public LiveData<PagedList<CurrentXkcdComic>> getCurrentComic() {
        return comicsData;
    }
}
