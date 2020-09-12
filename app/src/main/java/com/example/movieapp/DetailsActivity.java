package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        imageView = findViewById(R.id.imageView);

        String object = getIntent().getStringExtra("object");
        Results results = new Gson().fromJson(object,Results.class);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(results.getTitle());
        }

        Picasso.get().load(Utils.IMAGE_URL + results.getBackdrop_path()).into(imageView);
    }
}