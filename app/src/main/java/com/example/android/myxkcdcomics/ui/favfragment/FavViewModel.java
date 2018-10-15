package com.example.android.myxkcdcomics.ui.favfragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.android.myxkcdcomics.database.FavComic;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;
import com.example.android.myxkcdcomics.repository.XkcdRepository;

import java.util.List;

import static com.example.android.myxkcdcomics.utils.Constants.PAGE_SIZE;
import static com.example.android.myxkcdcomics.utils.Constants.PREFETCH_NUMBER;

public class FavViewModel extends ViewModel {

    private XkcdRepository xkcdRepository;
    private LiveData<PagedList<FavComic>> favComicsList;

    public FavViewModel() {
        xkcdRepository = XkcdRepository.getInstance();

        init();
    }

    private void init() {
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(PREFETCH_NUMBER)
                .setPageSize(PAGE_SIZE)
                .build();

        favComicsList = new LivePagedListBuilder<>(XkcdRepository.getInstance()
                .getAllFavs(),
                pagedListConfig).build();
    }

    public LiveData<PagedList<FavComic>> getFavComics() {
        return favComicsList;
    }

    public List<CurrentXkcdComic> getFavList() {
        return xkcdRepository.getFavComicsList();
    }
}
