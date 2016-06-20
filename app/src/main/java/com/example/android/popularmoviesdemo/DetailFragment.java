package com.example.android.popularmoviesdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailFragment extends Fragment {

    static final String DETAIL_URI = "URI";
    public static final String EXTRA_MOVIE = "movie";
    Movie mMovie;
    ImageView poster;
    TextView average;
    TextView date;
    TextView title;
    TextView description;
    CheckBox favoriteCheckBox;
    SharedPreferenceUtils sharedPreferenceUtils = new SharedPreferenceUtils();
    private Button button;
    private Button button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            retrieveMovieParcelable();
        }




        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.content_movie_detail, container, false);

        average = (TextView) rootview.findViewById(R.id.vote_average);
        date = (TextView) rootview.findViewById(R.id.release_date);
        title = (TextView) rootview.findViewById(R.id.movie_title);
        description = (TextView) rootview.findViewById(R.id.movie_description);
        poster = (ImageView) rootview.findViewById(R.id.movie_poster);
        favoriteCheckBox = (CheckBox) rootview.findViewById(R.id.favoriteCheckBox);
        button = (Button) rootview.findViewById(R.id.Trailer);
        button2 = (Button) rootview.findViewById(R.id.Review);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchTrailer(null);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchReview(null);
            }
        });
        return rootview;
    }
    private void retrieveMovieParcelable() {
        Bundle arguments = getArguments();
        arguments.getParcelable(DetailFragment.EXTRA_MOVIE);
        mMovie = arguments.getParcelable(EXTRA_MOVIE);

        if (mMovie != null) {

            loadMovieDetails(mMovie);

        } else {
            if (getActivity().getIntent().hasExtra(EXTRA_MOVIE)) {
                mMovie = getActivity().getIntent().getParcelableExtra(EXTRA_MOVIE);
            }
        }
    }
    public void loadMovieDetails(final Movie movie) {

        average.setText(mMovie.getAverage());
        date.setText(mMovie.getDate());
        title.setText(mMovie.getTitle());
        description.setText(mMovie.getDescription());

        favoriteCheckBox.setChecked(SharedPreferenceUtils.isFavorite(getActivity(), mMovie.getId()));

        favoriteCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferenceUtils.isFavorite(getActivity(), mMovie.getId())) {
                    SharedPreferenceUtils.removeFavorite(getActivity(), mMovie);
                } else {
                    SharedPreferenceUtils.addFavorite(getContext(), mMovie);
                }
            }
        });
        Picasso.with(getActivity())
                .load(mMovie.getPoster())
                .into(poster);
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
                    Intent intent = new Intent(getActivity(), ReviewActivity.class);
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























































