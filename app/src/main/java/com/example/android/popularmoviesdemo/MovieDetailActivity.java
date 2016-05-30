package com.example.android.popularmoviesdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.*;
import android.support.v7.recyclerview.*;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Request;
import retrofit.client.Response;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";
    Movie mMovie;
    ImageView poster;
    TextView average;
    TextView date;
    TextView title;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        average = (TextView) findViewById(R.id.vote_average);
        date = (TextView) findViewById(R.id.release_date);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);
        average.setText(mMovie.getAverage());
        date.setText(mMovie.getDate());
        title.setText(mMovie.getTitle());
        description.setText(mMovie.getDescription());
        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(poster);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Trailer) {
            LaunchTrailer(null);
        } else if (id == R.id.Review) {
            LaunchReview(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void LaunchTrailer(View view) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.TMDB_API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieTrailerEndpoint service = restAdapter.create(MovieTrailerEndpoint.class);
        service.LaunchTrailer(mMovie.getId(), new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {
                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String result = sb.toString();
                try {
                    JSONObject root = new JSONObject(result);
                    String youTubeKey = root.getJSONArray("results").getJSONObject(0).getString("key");
                    Uri.Builder builder = new Uri.Builder();
                    String url = builder
                            .path("https://www.youtube.com")
                            .appendPath("watch")
                            .appendQueryParameter("v", youTubeKey)
                            .build().toString();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.youtube.com/watch?v=" + youTubeKey));
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
    public void LaunchReview(View view) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.TMDB_API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieReviewEndpoint service = restAdapter.create(MovieReviewEndpoint.class);
        service.LaunchReview(mMovie.getId(), new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {
                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String result = sb.toString();
                try {
                    JSONObject root = new JSONObject(result);
                    JSONArray array = root.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        String author = array.getJSONObject(i)
                                .getString("author");
                        String content = array.getJSONObject(i)
                                .getString("content");
                        Review review = new Review(author, content);
                        mMovie.putReview(review);
                    }
                    Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                    intent.putParcelableArrayListExtra("reviews", mMovie.getReviews());
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}












































































