package com.example.android.popularmoviesdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private ListView mListView;
    private ArrayAdapter<Review> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<Review> reviews = getIntent().getParcelableArrayListExtra("reviews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        adapter = new ArrayAdapter<Review> (getApplicationContext(), R.layout.review_container, R.id.review_content_textview, reviews);

        mListView = (ListView) findViewById(R.id.listview_review);
        mListView.setAdapter(adapter);
    }
}


