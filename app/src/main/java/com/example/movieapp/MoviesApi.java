package com.example.movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {

    @GET("movie")
    Call<Movies> getMovies(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("sort_by") String sort_by
    );

}
