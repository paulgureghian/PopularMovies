package com.example.android.popularmoviesdemo.Endpoints;

import com.example.android.popularmoviesdemo.POJOs.Movie;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MovieTrailerEndpoint {
    @GET("/movie/{id}/videos")

void LaunchTrailer (@Path("id") String param, Callback<Movie.MovieResult>cb);



}
