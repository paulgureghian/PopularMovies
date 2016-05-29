package com.example.android.popularmoviesdemo;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private ListView mListView;
    private ReviewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //    setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.listview_review);
        mAdapter = new ReviewsAdapter(this);

        mListView.setAdapter(mAdapter);
        mAdapter.setReviewList(reviews);
        ArrayList<Review> reviews = getIntent().getParcelableArrayListExtra("reviews");
    }
    public static class Adapter extends ListView.Adapter<Review> {
        private List<Review> mReviewList;
        private LayoutInflater mInflater;
        private Context mContext;
        public Adapter (Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }
    }



}
