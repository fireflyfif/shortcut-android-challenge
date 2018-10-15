package com.example.android.myxkcdcomics.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.android.myxkcdcomics.callbacks.ResultFromCallback;
import com.example.android.myxkcdcomics.database.FavComic;
import com.example.android.myxkcdcomics.repository.XkcdRepository;

public class DetailComicViewModel extends AndroidViewModel {

    private static final String TAG = DetailComicViewModel.class.getSimpleName();

    private XkcdRepository repository;
    private MutableLiveData<Boolean> isAddedToDb;
    private boolean isAdded;

    public DetailComicViewModel(Application application) {
        super(application);

        repository = XkcdRepository.getInstance(application);

        isAddedToDb = new MutableLiveData<>();
        isAddedToDb.postValue(false);

        isAdded = false;
    }

    public void insertInDb(FavComic favComic) {
        repository.insertItem(favComic);
    }

    public void deleteItem(String comicNum) {
        repository.deleteItem(comicNum);
    }

    public void isAddedToDb(String comicNum, ResultFromCallback resultFromCallback) {
        repository.addOrRemoveFromDb(comicNum, resultFromCallback);
    }

    public MutableLiveData<Boolean> addOrRemoveFromDb(String comicNum, FavComic favComic) {

        repository.addOrRemoveFromDb(comicNum, new ResultFromCallback() {
            @Override
            public void setResult(boolean isFav) {
                if (isFav) {
                    isAddedToDb.postValue(false);
                    Log.d(TAG, "Item is fav");
                } else {
                    isAddedToDb.postValue(true);
                    Log.d(TAG, "Item is NOT fav");
                }
            }
        });

        return isAddedToDb;
    }
}
