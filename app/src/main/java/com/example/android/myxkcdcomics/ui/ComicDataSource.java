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

import static com.example.android.myxkcdcomics.utils.Constants.BASE_URL;

public class ComicDataSource extends PageKeyedDataSource<Long, CurrentXkcdComic> {


    private static final String TAG = ComicDataSource.class.getSimpleName();

    private int comicId;
    private List<CurrentXkcdComic> comicsList = new ArrayList<>();

    ComicDataSource() {}

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback callback) {

        XkcdApplication.getInstance().getXkcdApi().getCurrentComic().enqueue(new Callback<CurrentXkcdComic>() {

            CurrentXkcdComic currentComic = new CurrentXkcdComic();

            @Override
            public void onResponse(@NonNull Call<CurrentXkcdComic> call,
                                   @NonNull Response<CurrentXkcdComic> response) {
                if (response.isSuccessful()) {
                    currentComic = response.body();

                    // Add recent comic to the list of comics
                    comicsList.add(currentComic);

                    // TODO: Check the logic below
                    comicId = currentComic.getNum();
                    int fetchPrevious = 10;
                    int i;
                    // Fetch the first 3 comics starting from current (last one)
                    for (i = comicId-1; i > (comicId - fetchPrevious) && i > 0; i--) {
                        comicsList.add(new CurrentXkcdComic(i));
                    }
                    comicId = i;

                    callback.onResult(comicsList, null, 2L);

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

        String baseUrl = BASE_URL;
        String endpointOfUrl = "/info.0.json";
        String newUrl = baseUrl + comicId + endpointOfUrl;
        Log.d(TAG, "New url: " + newUrl);


        Log.d(TAG, "Comic number after for loop: " + comicId);

        XkcdApplication.getInstance().getXkcdApi().getComicById(comicId).enqueue(new Callback<CurrentXkcdComic>() {

            CurrentXkcdComic currentComic = new CurrentXkcdComic();

            @Override
            public void onResponse(@NonNull Call<CurrentXkcdComic> call, @NonNull Response<CurrentXkcdComic> response) {
                if (response.isSuccessful()) {
                    currentComic = response.body();

                    callback.onResult(comicsList, comicId);
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
