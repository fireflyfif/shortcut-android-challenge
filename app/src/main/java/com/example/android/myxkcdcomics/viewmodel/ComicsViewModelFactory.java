package com.example.android.myxkcdcomics.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.myxkcdcomics.repository.FavComicsRepository;
import com.example.android.myxkcdcomics.ui.DetailComicViewModel;

public class ComicsViewModelFactory implements ViewModelProvider.Factory {

    private final FavComicsRepository repository;

    public ComicsViewModelFactory(FavComicsRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailComicViewModel.class)) {
            return (T) new DetailComicViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
