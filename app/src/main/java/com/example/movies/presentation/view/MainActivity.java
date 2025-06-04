package com.example.movies.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.presentation.adapter.MoviesAdapter;
import com.example.movies.presentation.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private final static int SPAN_COUNT = 2;

    private MoviesAdapter moviesAdapter;

    private MainViewModel viewModel;

    private FloatingActionButton buttonGoToFavorites;
    private RecyclerView movieRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setUpRecyclerView();

        viewModel = new ViewModelProvider(this)
                .get(MainViewModel.class);

        setMovies();
        showLoadingListener();
        pagination();
        onMovieClickListener();
        viewModel.loadMovies();
        onFavoriteMovieClickListener();
    }

    private void onFavoriteMovieClickListener() {
        buttonGoToFavorites.setOnClickListener(view -> {
            Intent intent = FavoriteMoviesActivity.launchFavoriteMoviesScreen(MainActivity.this);
            startActivity(intent);
        });
    }

    private void onMovieClickListener() {
        moviesAdapter.setOnMovieClickListener(movie -> {
            Intent intent = MovieDetailActivity.launchMovieDetailScreen(this, movie);
            startActivity(intent);
        });
    }

    private void pagination() {
        moviesAdapter.setOnReachEndListener(viewModel::loadMovies);
    }

    private void showLoadingListener() {
        viewModel.getIsLoading().observe(
                this,
                loading -> {
                    if (loading) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void setMovies() {
        viewModel.getMovies().observe(
                this,
                movies -> moviesAdapter.setMovies(movies)
        );
    }

    private void setUpRecyclerView() {
        moviesAdapter = new MoviesAdapter();
        movieRecyclerView.setAdapter(moviesAdapter);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
    }

    private void initViews() {
        movieRecyclerView = findViewById(R.id.recyclerViewMovies);
        progressBar = findViewById(R.id.progressBar);
        buttonGoToFavorites = findViewById(R.id.buttonGoToFavorites);
    }
}