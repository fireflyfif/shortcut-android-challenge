package com.example.android.myxkcdcomics.repository;

import android.arch.paging.DataSource;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.myxkcdcomics.AppExecutors;
import com.example.android.myxkcdcomics.callbacks.ResultFromCallback;
import com.example.android.myxkcdcomics.database.FavComic;
import com.example.android.myxkcdcomics.database.dao.FavComicsDao;

public class FavComicsRepository {

    private static final String TAG = FavComicsRepository.class.getSimpleName();

    // Make the variable variable, so that memory barrier is inserted
    // All writes initiated prior to that of INSTANCE will be completed before
    // the INSTANCE is modified
    // source: https://stackoverflow.com/a/11640026/8132331
    private static volatile FavComicsRepository INSTANCE;

    private final FavComicsDao favComicsDao;
    private final AppExecutors executors;


    private FavComicsRepository(FavComicsDao comicsDao, AppExecutors executors) {
        this.favComicsDao = comicsDao;
        this.executors = executors;
    }

    public static FavComicsRepository getInstance(FavComicsDao comicsDao,
                                                  AppExecutors executors) {
        if (INSTANCE == null) {
            // If there is no instance available, create a new one
            synchronized (FavComicsRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavComicsRepository(comicsDao, executors);
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
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favComicsDao.insertComic(favComic);
            }
        });
    }

    /**
     * Method for deleting an item by its number
     *
     * @param comicNumber the number of the comic being deleted
     */
    public void deleteItem(final String comicNumber) {
        executors.diskIO().execute(new Runnable() {
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
        executors.diskIO().execute(new Runnable() {
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
