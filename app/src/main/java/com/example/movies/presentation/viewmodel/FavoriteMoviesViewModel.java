package com.example.movies.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.data.MoviesDao;
import com.example.movies.data.MoviesDatabase;
import com.example.movies.data.movie.Movie;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private final MoviesDao moviesDao;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MoviesDatabase.getInstance(application).moviesDao();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return moviesDao.getFavoriteMovies();
    }
}
