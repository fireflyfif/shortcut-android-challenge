package com.example.android.myxkcdcomics.ui;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.myxkcdcomics.XkcdApplication;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicDataSource extends PageKeyedDataSource<Long, CurrentXkcdComic> {

    private static final String TAG = ComicDataSource.class.getSimpleName();

    // Variable for the comic number
    private int comicId;

    ComicDataSource() {
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

                    // Add recent comic to the list of comics
                    comicsList.add(currentComic);

                    // Get the recent number of the comic
                    comicId = currentComic.getNum();
                    // Decrease the number of the comic so that it is
                    // used in the loadAfter() method
                    comicId = comicId - 1;

                    callback.onResult(comicsList, null, comicId);

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
        // Ignore this, because we don't need to load anything before the initial load of data
    }

    @Override
    public void loadAfter(@NonNull final LoadParams params, @NonNull final LoadCallback callback) {
        Log.i(TAG, "loadAfter: Loading: " + params.key + " Count: " + params.requestedLoadSize + " ComicId: " + comicId);

        XkcdApplication.getInstance().getXkcdApi().getComicById(comicId).enqueue(new Callback<CurrentXkcdComic>() {

            CurrentXkcdComic currentComic = new CurrentXkcdComic();
            List<CurrentXkcdComic> comicsListAfter = new ArrayList<>();

            @Override
            public void onResponse(@NonNull Call<CurrentXkcdComic> call, @NonNull Response<CurrentXkcdComic> response) {
                if (response.isSuccessful()) {
                    currentComic = response.body();

                    if (currentComic != null) {
                        // Decrement the comicId by 1
                        comicId--;

                        // Stop when the comic number reach 1
                        if (comicId == 1) {
                            return;
                        }

                        // Add comics to the new list of comics
                        comicsListAfter.add(currentComic);
                        Log.d(TAG, "loadAfter: Comic number after decrease: " + comicId);
                        callback.onResult(comicsListAfter, comicId);
                        Log.d(TAG, "loadAfter: List of comics, loadAfter: " + comicsListAfter.size());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentXkcdComic> call, @NonNull Throwable t) {
                Log.d(TAG, "Response code from initial load, onFailure: " + t.getMessage());
            }
        });
    }
}
