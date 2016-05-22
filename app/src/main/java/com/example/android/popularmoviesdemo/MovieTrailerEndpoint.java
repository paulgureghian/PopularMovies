package com.example.android.popularmoviesdemo;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MovieTrailerEndpoint {
    @GET("/movie/{id}/videos")

void LaunchTrailer (@Path("id") String param, Callback<Movie.MovieResult>cb);



}
