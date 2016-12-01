package com.udacity.android.tmdb.remote;

import com.udacity.android.tmdb.pojo.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by geovani on 11/30/16.
 */

public class MovieResponse {

    @SerializedName("results")
    private List<Movie> results;


    private MovieResponse(List<Movie> results) {
        this.results = results;
    }


    public List<Movie> getResults() {
        return results;
    }



}
