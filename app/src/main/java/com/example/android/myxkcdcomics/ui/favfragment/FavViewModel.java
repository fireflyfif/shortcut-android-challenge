package com.example.android.myxkcdcomics.ui.favfragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.android.myxkcdcomics.database.FavComic;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;
import com.example.android.myxkcdcomics.repository.XkcdRepository;

import java.util.List;

import static com.example.android.myxkcdcomics.utils.Constants.PAGE_SIZE;
import static com.example.android.myxkcdcomics.utils.Constants.PREFETCH_NUMBER;

public class FavViewModel extends AndroidViewModel {

    private XkcdRepository xkcdRepository;
    private LiveData<PagedList<FavComic>> favComicsList;

    public FavViewModel(Application application) {
        super(application);
        xkcdRepository = XkcdRepository.getInstance(application);

        init(application);
    }

    private void init(Application application) {
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(PREFETCH_NUMBER)
                .setPageSize(PAGE_SIZE)
                .build();

        favComicsList = new LivePagedListBuilder<>(XkcdRepository.getInstance(application)
                .getAllFavs(),
                pagedListConfig).build();
    }

    public LiveData<PagedList<FavComic>> getFavComics() {
        return favComicsList;
    }

    public List<FavComic> getFavList() {
        return xkcdRepository.getFavComicsList();
    }
}
