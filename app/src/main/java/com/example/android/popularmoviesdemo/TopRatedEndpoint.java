package com.example.android.popularmoviesdemo;


import retrofit.Callback;
import retrofit.http.GET;

public interface TopRatedEndpoint {

    @GET("/movie/top_rated ")
    void getPopularMovies(Callback<Movie.MovieResult> cb);


}
