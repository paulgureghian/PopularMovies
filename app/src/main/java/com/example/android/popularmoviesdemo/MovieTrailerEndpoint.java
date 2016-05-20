package com.example.android.popularmoviesdemo;

import retrofit.Callback;
import retrofit.http.GET;

public interface MovieTrailerEndpoint {
    @GET("/movie/{id}/videos")

void getlaunchTrailer (Callback<Movie.MovieResult>cb);



}
