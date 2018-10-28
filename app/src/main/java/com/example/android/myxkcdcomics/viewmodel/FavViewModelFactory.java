package com.example.android.myxkcdcomics.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.myxkcdcomics.repository.FavComicsRepository;
import com.example.android.myxkcdcomics.ui.DetailComicViewModel;
import com.example.android.myxkcdcomics.ui.favfragment.FavViewModel;

public class FavViewModelFactory implements ViewModelProvider.Factory {
    private final FavComicsRepository repository;

    public FavViewModelFactory(FavComicsRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavViewModel.class)) {
            return (T) new FavViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
