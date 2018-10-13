package com.example.android.myapplication.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class XkcdApiManager {

    private static final String BASE_URL = "https://xkcd.com/";

    /**
     * Method that creates the Retrofit instance that will make the call to the API
     * @return a {@link Call} which represents the HTTP request
     */
    public static XkcdService create() {
        // Add logging interceptor to Log the network call
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        // Build the retrofit instance that will make the call to the API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(XkcdService.class);
    }
}
