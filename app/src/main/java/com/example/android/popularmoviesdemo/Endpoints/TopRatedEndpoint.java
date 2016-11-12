package com.example.android.popularmoviesdemo.Endpoints;


import com.example.android.popularmoviesdemo.POJOs.Movie;

import retrofit.Callback;
import retrofit.http.GET;

public interface TopRatedEndpoint {

    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);


}
