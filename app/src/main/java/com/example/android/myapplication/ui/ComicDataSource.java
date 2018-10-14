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

    private XkcdApplication appController;
    private int comicId;
    private List<CurrentXkcdComic> comicsList = new ArrayList<>();

    public ComicDataSource(XkcdApplication appController) {
        this.appController = appController;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {

        XkcdApplication.getInstance().getXkcdApi().getComicById(200).enqueue(new Callback<CurrentXkcdComic>() {

            CurrentXkcdComic currentComic = new CurrentXkcdComic();

            @Override
            public void onResponse(@NonNull Call<CurrentXkcdComic> call,
                                   @NonNull Response<CurrentXkcdComic> response) {
                if (response.isSuccessful()) {
                    currentComic = response.body();

                    comicsList.add(currentComic);

                    // TODO: Check the logic below
                    comicId = currentComic.getNum();
                    int fetchPrevious = 3;

                    // Fetch the first 5 comics starting from current (last one)
                    for (int i = comicId; i > (comicId - fetchPrevious) && i > 0; i--) {
                        //comicsList.add(new CurrentXkcdComic(i));

                    }

                    //callback.onResult(comicsList, null, 2L);

                    Log.d(TAG, "List of comics loadInitial: " + comicsList.size());

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
    public void loadAfter(@NonNull final LoadParams params, @NonNull final LoadCallback callback) {

        XkcdApplication.getInstance().getXkcdApi().getComicById(params.requestedLoadSize).enqueue(new Callback<CurrentXkcdComic>() {

            CurrentXkcdComic currentComic = new CurrentXkcdComic();
            //List<CurrentXkcdComic> comicsList = new ArrayList<>();

            @Override
            public void onResponse(@NonNull Call<CurrentXkcdComic> call, @NonNull Response<CurrentXkcdComic> response) {
                if (response.isSuccessful()) {
                    currentComic = response.body();
                    comicsList.add(currentComic);

                    long nextKey = comicId;
                    if (nextKey > 0) {
                        nextKey = - 1;
                    }

                    callback.onResult(comicsList, nextKey);
                    Log.d(TAG, "List of comics, loadAfter: " + comicsList.size());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentXkcdComic> call, @NonNull Throwable t) {
                Log.d(TAG, "Response code from initial load, onFailure: " + t.getMessage());
            }
        });
    }
}
