package com.example.movieapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movieapp.Movies;
import com.example.movieapp.Results;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<Results> getAllMovies();

    @Query("SELECT * FROM movies WHERE id == :movieId")
    Results getMovieById(int movieId);

    @Query("DELETE FROM movies") //все удаляет
    void deleteAllMovies();

    @Insert
    void insertMovie(Results movie); //добавить 1 элемент

    @Delete
    void deleteMovie(Results movie); //удлаить 1 элемент
}
