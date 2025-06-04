package com.example.movies.data.movie;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {

    @SerializedName("docs")
    private final List<Movie> movies;

    public MoviesResponse(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    @NonNull
    @Override
    public String toString() {
        return "Docs{" +
                "movies=" + movies +
                '}';
    }
}
