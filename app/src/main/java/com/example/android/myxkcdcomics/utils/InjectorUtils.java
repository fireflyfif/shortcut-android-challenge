package com.example.android.myxkcdcomics.utils;

import android.content.Context;

import com.example.android.myxkcdcomics.AppExecutors;
import com.example.android.myxkcdcomics.database.ComicsDatabase;
import com.example.android.myxkcdcomics.repository.FavComicsRepository;
import com.example.android.myxkcdcomics.viewmodel.ComicsViewModelFactory;
import com.example.android.myxkcdcomics.viewmodel.FavViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for Xkcd
 * source: https://github.com/googlecodelabs/android-build-an-app-architecture-components/blob/arch-training-steps/app/src/main/java/com/example/android/sunshine/utilities/InjectorUtils.java#L33
 */
public class InjectorUtils {

    private static FavComicsRepository provideRepository(Context context) {
        ComicsDatabase database = ComicsDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();

        return FavComicsRepository.getInstance(database.favComicsDao(), executors);
    }

    public static ComicsViewModelFactory provideDetailComicViewModelFactory(Context context) {
        FavComicsRepository repository = provideRepository(context.getApplicationContext());
        return new ComicsViewModelFactory(repository);
    }

    public static FavViewModelFactory provideFavComicViewModelFactory(Context context) {
        FavComicsRepository repository = provideRepository(context.getApplicationContext());
        return new FavViewModelFactory(repository);
    }

}
