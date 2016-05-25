package com.example.android.popularmoviesdemo;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MovieReviewEndpoint {

        @GET("/movie/{id}/reviews")

    void LaunchReview (@Path("id") String param, Callback<Movie.MovieResult>cb);

}
