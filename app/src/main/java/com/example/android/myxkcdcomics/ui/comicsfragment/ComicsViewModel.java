package com.example.android.myxkcdcomics.ui.comicsfragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.example.android.myxkcdcomics.AppExecutors;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;
import com.example.android.myxkcdcomics.ui.comicsfragment.paging.ComicDataSourceFactory;

import static com.example.android.myxkcdcomics.utils.Constants.PAGE_SIZE;
import static com.example.android.myxkcdcomics.utils.Constants.PREFETCH_NUMBER;

public class ComicsViewModel extends ViewModel {

    private static final String TAG = ComicsViewModel.class.getSimpleName();

    private LiveData<PagedList<CurrentXkcdComic>> comicsData;
    private ComicDataSourceFactory comicDataSourceFactory;


    public ComicsViewModel() {
        initCurrentComic();
    }

    private void initCurrentComic() {
        // Get an instance of the DataSourceFactory
        comicDataSourceFactory = new ComicDataSourceFactory();

        // Configure thePagedList.Config
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPrefetchDistance(PREFETCH_NUMBER)
                .setPageSize(PAGE_SIZE)
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
