package com.example.android.myxkcdcomics.ui.favfragment;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.android.myxkcdcomics.database.FavComic;
import com.example.android.myxkcdcomics.repository.FavComicsRepository;

import static com.example.android.myxkcdcomics.utils.Constants.PAGE_SIZE;
import static com.example.android.myxkcdcomics.utils.Constants.PREFETCH_NUMBER;

public class FavViewModel extends ViewModel {

    private FavComicsRepository repository;
    private LiveData<PagedList<FavComic>> favComicsList;

    public FavViewModel(FavComicsRepository repository) {
        this.repository = repository;

        init();
    }

    private void init() {
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(PREFETCH_NUMBER)
                .setPageSize(PAGE_SIZE)
                .build();

        favComicsList = new LivePagedListBuilder<>(repository
                .getAllFavs(),
                pagedListConfig).build();
    }

    public LiveData<PagedList<FavComic>> getFavComics() {
        return favComicsList;
    }

    public void deleteAllComics() {
        repository.deleteAllItems();
    }

    public LiveData<PagedList<FavComic>> refreshFavComics(Application application) {
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(PREFETCH_NUMBER)
                .setPageSize(PAGE_SIZE)
                .build();

        favComicsList = new LivePagedListBuilder<>(repository
                .getAllFavs(),
                pagedListConfig).build();

        return favComicsList;
    }
}
