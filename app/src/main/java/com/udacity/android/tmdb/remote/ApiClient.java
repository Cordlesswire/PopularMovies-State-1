package com.udacity.android.tmdb.remote;

import com.udacity.android.tmdb.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by geovani on 11/30/16.
 */

public class ApiClient {

    public static final String BASE_URL   = "http://api.themoviedb.org/3/movie/";
    public static final String BASE_IMAGE = "http://image.tmdb.org/t/p/w185/";
    public final static String API_KEY = BuildConfig.TMDB_API_KEY;
    private static Retrofit mRetrofit = null;


    public static Retrofit getClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }


}
