package com.example.movieapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieapp.Results;

@Database(entities = {Results.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DB_NAME = "movies.db";
    private static MovieDatabase database;

    public static MovieDatabase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context,MovieDatabase.class, DB_NAME).build();
        }
        return database;
    }
    public abstract MovieDao getMovieDao(); //за счет него мы получим наши классы
}
