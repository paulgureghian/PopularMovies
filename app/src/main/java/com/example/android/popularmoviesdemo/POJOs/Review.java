package com.example.android.popularmoviesdemo.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

public final class Review implements Parcelable {

    private String author;
    private String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }
    public String getAuthor() {
        return this.author;
    }
    public String getContent() {
        return this.content;
    }
    @Override
    public String toString() {
        return "Author: " +
                author +
                "\n" +
                content +
                "\n";
    }
    protected Review(Parcel in) {
        author = in.readString();
        content = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }
        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}








