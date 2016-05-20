package com.example.android.popularmoviesdemo;


import android.provider.MediaStore;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MovieTrailerEndpoint {

    @GET("/movie/{id}/videos")

    void getlaunchTrailer (Call<List<MediaStore.Video>> movieList(@Path("id") int movieId);
}
