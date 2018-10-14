package com.example.android.myxkcdcomics.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.myxkcdcomics.XkcdApplication;
import com.example.android.myxkcdcomics.model.CurrentXkcdComic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Repository pattern that is a Singleton class.
It abstracts the call to the XkcdService object
 */
public class XkcdRepository {

    // Make the variable variable, so that memory barrier is inserted
    // All writes initiated prior to that of INSTANCE will be completed before
    // the INSTANCE is modified
    // source: https://stackoverflow.com/a/11640026/8132331
    private static volatile XkcdRepository INSTANCE;

    private static final String TAG = XkcdRepository.class.getSimpleName();


    // Private constructor
    public XkcdRepository() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of " +
                    "this class.");
        }
    }

    public static XkcdRepository getInstance() {
        if (INSTANCE == null) {
            // If there is no instance available, create a new one
            synchronized (XkcdRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new XkcdRepository();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Getter method that gets the loaded comic data
     * @return the loaded comic data from the XKCD API
     */
    public LiveData<CurrentXkcdComic> getCurrentComics() {
        return loadCurrentComics();
    }

    /**
     * Method that loads the current comics from the XKCD API
     * @return the Mutable data of the current comics
     */
    private LiveData<CurrentXkcdComic> loadCurrentComics() {

        final MutableLiveData<CurrentXkcdComic> comicsData = new MutableLiveData<>();

        XkcdApplication.getInstance().getXkcdApi().getCurrentComic().enqueue(new Callback<CurrentXkcdComic>() {

            CurrentXkcdComic currentXkcdComics = new CurrentXkcdComic();

            @Override
            public void onResponse(@NonNull Call<CurrentXkcdComic> call, @NonNull Response<CurrentXkcdComic> response) {
                if (response.isSuccessful()) {
                    currentXkcdComics = response.body();
                    if (currentXkcdComics != null) {
                        // Set the value of the current comics
                        comicsData.setValue(currentXkcdComics);
                        Log.d(TAG, "Current comics loaded successfully!");
                    } else {
                        comicsData.setValue(null);
                        Log.d(TAG, "Current comics NOT loaded successfully!");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentXkcdComic> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure! " + t.getMessage());
            }
        });

        return comicsData;
    }
}
