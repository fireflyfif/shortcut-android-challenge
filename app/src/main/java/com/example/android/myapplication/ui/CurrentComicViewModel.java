package com.example.android.myapplication.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.myapplication.model.CurrentXkcdComic;
import com.example.android.myapplication.repository.XkcdRepository;

public class CurrentComicViewModel extends ViewModel {

    private LiveData<CurrentXkcdComic> comicData;

    public CurrentComicViewModel() {
    }

    public void initCurrentComic() {
        if (comicData != null) {
            return;
        }

        comicData = XkcdRepository.getInstance().getCurrentComics();
    }

    public LiveData<CurrentXkcdComic> getCurrentComic() {
        return comicData;
    }
}
