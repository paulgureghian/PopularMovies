package com.example.android.popularmoviesdemo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Movie implements Parcelable {

    @SerializedName("release_date")
    private String date;

    private String title;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String description;

    @SerializedName("backdrop_path")
    private String backdrop;

    public Movie() {}

    protected Movie(Parcel in) {
        date = in.readString();
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        backdrop = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getDate() {return date;}
    public void  setDate (String release_dates){this.date = release_dates;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return "http://image.tmdb.org/t/p/w500" + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return "http://image.tmdb.org/t/p/w500" + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeString(backdrop);

    }

    public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }


}




























