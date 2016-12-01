package com.udacity.android.tmdb.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by geovani on 11/30/16.
 */

public interface ApiEndpointInterface
{
    @GET("popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);
    @GET("top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
