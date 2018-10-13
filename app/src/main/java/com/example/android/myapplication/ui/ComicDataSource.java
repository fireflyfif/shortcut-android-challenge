package com.example.android.myapplication.ui;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.myapplication.XkcdApplication;
import com.example.android.myapplication.model.CurrentXkcdComic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicDataSource extends PageKeyedDataSource<Long, CurrentXkcdComic> {


    private static final String TAG = ComicDataSource.class.getSimpleName();

    public ComicDataSource() {
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {

        XkcdApplication.getInstance().getXkcdApi().getCurrentComic().enqueue(new Callback<CurrentXkcdComic>() {

            CurrentXkcdComic currentComic = new CurrentXkcdComic();
            List<CurrentXkcdComic> comicsList = new ArrayList<>();

            @Override
            public void onResponse(@NonNull Call<CurrentXkcdComic> call,
                                   @NonNull Response<CurrentXkcdComic> response) {
                if (response.isSuccessful()) {
                    currentComic = response.body();

                    // TODO: Check the logic below
                    long comicId = currentComic.getNum();

                    for (int i = 0; i < comicId; i--) {
                        comicsList.add(new CurrentXkcdComic(i));
                    }

                    callback.onResult(comicsList, null, 2L);

                    Log.d(TAG, "List of comics loadInitial : " + comicsList.size());

                } else {

                    Log.d(TAG, "Response code from initial load: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentXkcdComic> call, @NonNull Throwable t) {
                Log.d(TAG, "Response code from initial load, onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }
}
