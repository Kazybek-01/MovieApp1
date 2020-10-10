package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.room.MovieDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwitchCompat switchButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    List<Results> resultsList;
    MoviesAdapter adapter;
    TextView popular, rating;
    String sort_by;

    private static MovieDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = MovieDatabase.getInstance(MainActivity.this);
        recyclerView = findViewById(R.id.recyclerView);
        switchButton = findViewById(R.id.switch1);
        popular = findViewById(R.id.popular);
        rating = findViewById(R.id.rating);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        loadImages();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImages();
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sort_by = "vote_average.desc";
                    popular.setTextColor(getResources().getColor(R.color.colorText2,getTheme()));
                    rating.setTextColor(getResources().getColor(R.color.colorText,getTheme()));
                    loadImages();
                } else {
                    sort_by = "popularity.desc";
                    rating.setTextColor(getResources().getColor(R.color.colorText2,getTheme()));
                    popular.setTextColor(getResources().getColor(R.color.colorText,getTheme()));
                    loadImages();
                }
            }
        });

    }
    public void insertMovie(Results movie){
        new InsertTask().execute(movie);
    }
    private static class InsertTask extends AsyncTask<Results,Void,Void>{
        @Override
        protected Void doInBackground(Results... movies) {
            if(movies != null && movies.length > 0){
                database.getMovieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    public void loadImages(){
        swipeRefreshLayout.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi api = retrofit.create(MoviesApi.class);
        Call<Movies> call = api.getMovies(Utils.api_key,"en-US",sort_by);

        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                swipeRefreshLayout.setRefreshing(false);
                if(response.isSuccessful() && response.body() != null){
//                    resultsList.clear();
                    resultsList = response.body().getResults();
                    Log.i("tagcode", String.valueOf(response.code()));
                    Log.i("tag", response.body().getResults().get(0).getTitle());
                    adapter = new MoviesAdapter(MainActivity.this,resultsList);
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    recyclerView.setAdapter(adapter);



                    adapter.setOnMovieItemClickListener(new MoviesAdapter.MovieItemClick() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                            Results results = resultsList.get(position); //за счет позиции берем тот объект на который нажали
                            String object = new Gson().toJson(results);
                            intent.putExtra("object",object);
                            startActivity(intent);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}