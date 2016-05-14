package com.example.android.popularmoviesdemo;


import retrofit.Callback;
import retrofit.http.GET;

public interface TopRatedEndpoint {

    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);


}
