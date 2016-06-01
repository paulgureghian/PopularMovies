package com.example.android.popularmoviesdemo;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    public void saveFavorites(Context context, List<Movie> favorites) {

        Movie movie = new Movie();
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(movie.getId(), new Gson().toJson(movie));
        editor.commit();
    }
    public void addFavorite(Context context, Movie movie) {
        List<Movie> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Movie>();
        favorites.add(movie);
        saveFavorites(context, favorites);
    }
    public void removeFavorite(Context context, Movie movie) {
        ArrayList<Movie> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(movie);
            saveFavorites(context, favorites);
        }
    }
    public ArrayList<Movie> getFavorites(Context context) {
        SharedPreferences settings;
        List<Movie> favorites;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Movie[] favoriteItems = gson.fromJson(jsonFavorites,
                    Movie[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Movie>(favorites);
        } else
            return null;
        return (ArrayList<Movie>) favorites;
    }
}






















