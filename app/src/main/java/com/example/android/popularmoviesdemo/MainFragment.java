package com.example.android.popularmoviesdemo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainFragment extends Fragment {

    private static AppCompatActivity mActivity;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    String sortType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new MoviesAdapter(getContext());

        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            movies.add(new Movie());
        }
        mAdapter.setMovieList(movies);

        sortType = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getResources().getString(R.string.pref_sort_key),
                getResources().getString(R.string.pref_sort_most_popular));
        if (sortType.equals(getResources().getString(R.string.pref_sort_most_popular))) {
            getPopularMovies();
        } else if (sortType.equals(getResources().getString(R.string.pref_sort_top_rated))) {
            getTopRatedMovies();
        } else if (sortType.equals("Favorites")) {
            loadFavoriteMovies();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        String latestSortType = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getResources().getString(R.string.pref_sort_key),
                getResources().getString(R.string.pref_sort_most_popular));
        if (!sortType.equals(latestSortType)) {
            Log.i("MainActivity", "sort order changed");
            if (latestSortType.equals(getResources().getString(R.string.pref_sort_most_popular))) {
                getPopularMovies();
            } else if (latestSortType.equals(getResources().getString(R.string.pref_sort_top_rated))) {
                getTopRatedMovies();
            } else if (latestSortType.equals("Favorites")) {
                loadFavoriteMovies();
            }
            sortType = latestSortType;
        }
    }
    public void loadFavoriteMovies() {
        mAdapter.setMovieList(Arrays.asList(SharedPreferenceUtils.getFavorites(getActivity())));
    }
    public void getPopularMovies() {
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
        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }
            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
    public void getTopRatedMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.TMDB_API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        TopRatedEndpoint service = restAdapter.create(TopRatedEndpoint.class);
        service.getTopRatedMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }
            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
    public static   class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
    public static   class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {
        private List<Movie> mMovieList;
        private LayoutInflater mInflater;
        private Context mContext;

        public MoviesAdapter(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view = mInflater.inflate(R.layout.row_movie, parent, false);
            final MovieViewHolder viewHolder = new MovieViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

             if (MainActivity.mTwoPane){

                 Bundle args = new Bundle();
                 int position;
                 position = viewHolder.getAdapterPosition();
                 args.putParcelable(DetailFragment.EXTRA_MOVIE, mMovieList.get(position));
                 DetailFragment fragment = new DetailFragment();
                 fragment.setArguments(args);


                 mActivity.getSupportFragmentManager().beginTransaction()
                         .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                         .commit();

                 Log.i("MainFrag", "tablet");

             }else {
                 int position = viewHolder.getAdapterPosition();
                 Intent intent = new Intent(mContext, MovieDetailActivity.class);
                 intent.putExtra(DetailFragment.EXTRA_MOVIE, mMovieList.get(position));
                 mContext.startActivity(intent);
             }







                }
            });
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position) {
            Movie movie = mMovieList.get(position);
            Picasso.with(mContext)
                    .load(movie.getPoster())
                    .placeholder(R.color.colorAccent)
                    .into(holder.imageView);
        }
        @Override
        public int getItemCount() {
            return (mMovieList == null) ? 0 : mMovieList.size();
        }

        public void setMovieList(List<Movie> movieList) {
            this.mMovieList = new ArrayList<>();
            this.mMovieList.addAll(movieList);
            notifyDataSetChanged();
        }
    }
       @Override
       public void onAttach (Activity activity) {
           super.onAttach(activity);
           mActivity = (AppCompatActivity) activity;
       }
}

