package com.example.android.myxkcdcomics.repository;

import android.app.Application;
import android.arch.paging.DataSource;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.myxkcdcomics.AppExecutors;
import com.example.android.myxkcdcomics.callbacks.ResultFromCallback;
import com.example.android.myxkcdcomics.database.ComicsDatabase;
import com.example.android.myxkcdcomics.database.FavComic;
import com.example.android.myxkcdcomics.database.dao.FavComicsDao;

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
    private FavComicsDao favComicsDao;


    // Private constructor
    // TODO: Remove the Application from the scope of the Repository
    private XkcdRepository(Application application) {
        //ComicsDatabase comicsDatabase = ComicsDatabase.getInstance(application);
        //favComicsDao = comicsDatabase.favComicsDao();
    }

    public static XkcdRepository getInstance(Application application) {
        if (INSTANCE == null) {
            // If there is no instance available, create a new one
            synchronized (XkcdRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new XkcdRepository(application);
                }
            }
        }

        return INSTANCE;
    }


}
