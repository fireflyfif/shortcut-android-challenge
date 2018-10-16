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
    private XkcdRepository(Application application) {
        ComicsDatabase comicsDatabase = ComicsDatabase.getInstance(application);
        favComicsDao = comicsDatabase.favComicsDao();
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

    public DataSource.Factory<Integer, FavComic> getAllFavs() {
        return favComicsDao.getAllFavComics();
    }

    /**
     * Method for inserting a new item in the database
     *
     * @param favComic the object being saved in the db
     */
    public void insertItem(final FavComic favComic) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favComicsDao.insertComic(favComic);
            }
        });
    }

    public void deleteItem(final String comicNumber) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Item is deleted from the db!");
                favComicsDao.deleteComic(comicNumber);
            }
        });
    }

    // Delete all list of favorite comics
    // Create a warning dialog for the user before allowing them to delete all data
    public void deleteAllItems() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favComicsDao.deleteAllData();
            }
        });
    }

    /*
    Get an item by Id from the database
     */
    public void addOrRemoveFromDb(String comicNum, ResultFromCallback callback) {
        new getItemByNum(comicNum, favComicsDao, callback).execute();
    }

    /*
    Query the item on a background thread via AsyncTask
     */
    private static class getItemByNum extends AsyncTask<Void, Void, Boolean> {

        private String comicNum;
        private FavComicsDao favComicsDao;
        private ResultFromCallback callback;

        public getItemByNum(String comicNum, FavComicsDao favComicsDao,
                            ResultFromCallback resultFromCallback) {
            this.comicNum = comicNum;
            this.favComicsDao = favComicsDao;
            this.callback = resultFromCallback;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean isFav = comicNum.equals(favComicsDao.getComicByNum(comicNum));
            Log.d(TAG, "doInBackground: Item is in the db: " + isFav);

            return isFav;
        }

        @Override
        protected void onPostExecute(Boolean isFav) {
            callback.setResult(isFav);
        }
    }

}
