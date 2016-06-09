package com.example.android.popularmoviesdemo;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.util.Map;

public class SharedPreferenceUtils {

    public static final String PREFS_NAME = "prefs";

    public SharedPreferenceUtils() {
    }
    public static void addFavorite(Context context, Movie movie) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(movie.getId(), new Gson().toJson(movie)).apply();
    }
    public static void removeFavorite(Context context, Movie id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(id).apply();
    }
    public static void removeFavorite(Context context, Movie movie) {
        removeFavorite(context, movie.getId());
    }
    public static Movie[] getFavorites(Context context) {
        Movie[] movieCollection;

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();

        movieCollection = new Movie[allEntries.size()];

        int count = 0;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            movieCollection[count++] = new Gson().fromJson(entry.getValue().toString(), Movie.class);
        }
        return movieCollection;
    }
    public static boolean isFavorite(DetailFragment context, String id) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).contains(id);
    }
}




