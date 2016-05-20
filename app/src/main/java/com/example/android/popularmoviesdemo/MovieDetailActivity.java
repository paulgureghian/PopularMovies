package com.example.android.popularmoviesdemo;

import android.content.ClipData;
import android.os.Bundle;
import android.support.design.*;
import android.support.design.BuildConfig;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

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
        setSupportActionBar(toolbar);
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
            launchTrailer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void launchTrailer() {

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviemdb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.TMDB_API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

    }


}

















