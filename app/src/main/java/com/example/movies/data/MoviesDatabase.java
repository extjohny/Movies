package com.example.movies.data;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movies.data.movie.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static final String DB_NAME = "favorite_movies.db";

    private static MoviesDatabase instance = null;

    public static MoviesDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    MoviesDatabase.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }

    public abstract MoviesDao moviesDao();
}
