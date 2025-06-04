package com.example.movies.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.presentation.adapter.MoviesAdapter;
import com.example.movies.presentation.viewmodel.FavoriteMoviesViewModel;

public class FavoriteMoviesActivity extends AppCompatActivity {

    private final static int SPAN_COUNT = 2;

    private RecyclerView favoritesRecyclerView;

    private FavoriteMoviesViewModel viewModel;

    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);

        initViews();
        setUpRecyclerView();

        viewModel = new ViewModelProvider(this)
                .get(FavoriteMoviesViewModel.class);

        setFavoriteMoviesToRv();
        onMovieClick();
    }

    private void setUpRecyclerView() {
        moviesAdapter = new MoviesAdapter();
        favoritesRecyclerView.setAdapter(moviesAdapter);
        favoritesRecyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
    }

    private void initViews() {
        favoritesRecyclerView = findViewById(R.id.recyclerViewFavoriteMovies);
    }

    private void setFavoriteMoviesToRv() {
        viewModel.getFavoriteMovies().observe(
                this,
                movies -> moviesAdapter.setMovies(movies)
        );
    }

    private void onMovieClick() {
        moviesAdapter.setOnMovieClickListener(movie -> {
            Intent intent = MovieDetailActivity.
                    launchMovieDetailScreen(FavoriteMoviesActivity.this, movie);
            startActivity(intent);
        });
    }

    public static Intent launchFavoriteMoviesScreen(Context context) {
        return new Intent(context, FavoriteMoviesActivity.class);
    }
}