package com.example.android.myxkcdcomics.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.myxkcdcomics.database.dao.FavComicsDao;

// Helper tutorial: https://medium.com/@ajaysaini.official/building-database-with-room-persistence-library-ecf7d0b8f3e9
@Database(
        entities = {FavComic.class},
        version = 1,
        exportSchema = false)
public abstract class ComicsDatabase extends RoomDatabase {

    private static ComicsDatabase INSTANCE;
    private static final Object LOCK = new Object();
    private static final String XKCD_DB = "comics.db";

    // Reference the DAO from the database class
    public abstract FavComicsDao favComicsDao();

    public static ComicsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = create(context);
            }
        }
        return INSTANCE;
    }

    private static ComicsDatabase create(Context context) {
        RoomDatabase.Builder<ComicsDatabase> databaseBuilder = Room.databaseBuilder(
                context.getApplicationContext(), ComicsDatabase.class, XKCD_DB);

        return (databaseBuilder.build());
    }

}
