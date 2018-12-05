package com.example.android.myxkcdcomics.data;

import com.example.android.myxkcdcomics.model.CurrentXkcdComic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface XkcdService {

    /*
    Endpoint that returns always the latest comics
     */
    @GET("/info.0.json")
    Call<CurrentXkcdComic> getCurrentComic();

    /*
    Endpoint that returns a requested comics by its id/number
     */
    @GET("{comicsId}/info.0.json")
    Call<CurrentXkcdComic> getComicById(@Path("comicsId") long comicsId);

}
