package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {

    TextView textViewTitle,originalTitle,rating,dataReliza,opisanie;
    TextView name,originalName,ratingResult,date,opisanieResult, trailerName;
    ImageView imageView;
    RecyclerView recyclerViewTrailer;
    List<Trailer> trailerList;
    TrailerAdapter trailerAdapter;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/montserrat_regular.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        setContentView(R.layout.activity_details);

        String object = getIntent().getStringExtra("object");
        Results results = new Gson().fromJson(object,Results.class);

        recyclerViewTrailer = findViewById(R.id.recyclerViewTrailer);
        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this));



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.TRAILER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi api = retrofit.create(MoviesApi.class);
        Call<TrailerMain> call = api.getTrailers(results.getId(),Utils.api_key,"en-US");

        call.enqueue(new Callback<TrailerMain>() {
            @Override
            public void onResponse(Call<TrailerMain> call, Response<TrailerMain> response) {
                if (response.isSuccessful()){
                    TrailerMain trailer = response.body();
                    trailerList = trailer.getTrailerList();
                    trailerAdapter = new TrailerAdapter(DetailsActivity.this, trailerList);
                    recyclerViewTrailer.setAdapter(trailerAdapter);

//                    trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.TrailerItemClick() {
//                        @Override
//                        public void onClickT(int position) {
//                            Trailer trailer = trailerList.get(position);
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + trailer.key));
//                            startActivity(intent);
//                        }
//                    });
                }
            }

            @Override
            public void onFailure(Call<TrailerMain> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imageView = findViewById(R.id.imageView);
        textViewTitle = findViewById(R.id.textViewTitle);
        originalTitle = findViewById(R.id.originalTitle);
        rating = findViewById(R.id.rating);
        dataReliza = findViewById(R.id.dataReliza);
        opisanie = findViewById(R.id.opisanie);
        name = findViewById(R.id.name);
        originalName = findViewById(R.id.originalName);
        ratingResult = findViewById(R.id.ratingResult);
        date = findViewById(R.id.date);
        opisanieResult = findViewById(R.id.opisanieResult);
        trailerName = findViewById(R.id.trailer_name);


        name.setText(results.getTitle());
        originalName.setText(results.getOriginal_title());
        ratingResult.setText(results.getVote_average());
        date.setText(results.getRelease_date());
        opisanieResult.setText(results.getOverview());


        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(results.getTitle());
        }

        Picasso.get().load(Utils.IMAGE_URL + results.getBackdrop_path()).into(imageView);
    }
}