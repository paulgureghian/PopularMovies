package com.example.android.popularmoviesdemo.Endpoints;

import com.example.android.popularmoviesdemo.POJOs.Movie;

import retrofit.Callback;
import retrofit.http.GET;

public interface MoviesApiService {
    @GET("/movie/popular")

    void getPopularMovies(Callback<Movie.MovieResult> cb);
}



