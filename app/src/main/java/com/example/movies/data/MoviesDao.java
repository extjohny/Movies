package com.example.movies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.data.movie.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<Movie>> getFavoriteMovies();

    @Query("SELECT * FROM favorite_movies WHERE id = :movieId")
    LiveData<Movie> getFavoriteMovie(int movieId);

    @Insert
    Completable addMovieToFavorites(Movie movie);

    @Query("DELETE FROM favorite_movies WHERE id = :movieId")
    Completable removeMovieFromFavorites(int movieId);
}
