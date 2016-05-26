package com.example.android.popularmoviesdemo;

import android.os.Parcel;
import android.os.Parcelable;

public final class Review implements Parcelable {

    private String author;
    private String content;

    public Review (String author, String content){
        this.author = author;
        this.content = content;
    }
    public String getAuthor() { return this.author; }
    public String  getContent() {return this.content; }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(author);
        parcel.writeString(content);
    }
    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];}
        };
    }



