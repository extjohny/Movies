package com.example.movies.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.data.movie.Movie;
import com.example.movies.presentation.adapter.ReviewsAdapter;
import com.example.movies.presentation.adapter.TrailersAdapter;
import com.example.movies.presentation.viewmodel.MovieDetailViewModel;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE_EXTRA_NAME = "movie";

    private MovieDetailViewModel viewModel;

    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    private RecyclerView trailersRecyclerView;
    private RecyclerView reviewsRecyclerView;
    private ImageView movieImageView;
    private ImageView favoriteImageView;
    private TextView nameTextView;
    private TextView yearTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();
        setUpRecyclerViews();

        Movie movie = getMovieFromExtra();
        int movieId = movie.getId();

        bindViews(movie);

        viewModel = new ViewModelProvider(this)
                .get(MovieDetailViewModel.class);

        viewModel.loadTrailers(movieId);
        viewModel.loadReviews(movieId);

        setTrailersToRv();
        setReviewsToRv();
        onTrailerClickListener();
        onReviewsReachEndListener(movieId);
        setFavoriteResToView(movie);
    }

    private void setFavoriteResToView(Movie movie) {
        Drawable favoriteOff = ContextCompat.getDrawable(this, R.drawable.favorite_off);
        Drawable favoriteOn = ContextCompat.getDrawable(this, R.drawable.favorite_on);

        viewModel.getFavoriteMovie(movie.getId()).observe(this, movieFromDb -> {
            if (movieFromDb == null) {
                favoriteImageView.setImageDrawable(favoriteOff);
            } else {
                favoriteImageView.setImageDrawable(favoriteOn);
            }
            favoriteImageView.setOnClickListener(view -> {
                if (movieFromDb == null) {
                    viewModel.addMovieToFavorite(movie);
                    favoriteImageView.setImageDrawable(favoriteOff);
                } else {
                    viewModel.removeMovieFromFavorite(movieFromDb.getId());
                    favoriteImageView.setImageDrawable(favoriteOn);
                }
            });
        });
    }

    private void onReviewsReachEndListener(int movieId) {
        reviewsAdapter.setOnReachEndListener(() -> viewModel.loadReviews(movieId));
    }

    private void setReviewsToRv() {
        viewModel.getReviews().observe(
                this,
                reviews -> reviewsAdapter.setReviews(reviews)
        );
    }

    private void onTrailerClickListener() {
        trailersAdapter.setOnTrailerClickListener(trailer -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(trailer.getUrl()));
            startActivity(intent);
        });
    }

    private void setTrailersToRv() {
        viewModel.getTrailers().observe(
                this,
                trailers ->
                        trailersAdapter.setTrailers(trailers)
        );
    }

    private void bindViews(Movie movie) {
        String url = movie.getPoster().getUrl();
        Glide.with(this)
                .load(url)
                .error(android.R.drawable.stat_notify_error)
                .into(movieImageView);
        nameTextView.setText(movie.getName());
        yearTextView.setText(String.valueOf(movie.getYear()));
        descriptionTextView.setText(movie.getDescription());
    }

    private Movie getMovieFromExtra() {
        return (Movie) getIntent().getSerializableExtra(MOVIE_EXTRA_NAME);
    }

    private void initViews() {
        movieImageView = findViewById(R.id.imageViewPoster);
        nameTextView = findViewById(R.id.textViewName);
        yearTextView = findViewById(R.id.textViewYear);
        favoriteImageView = findViewById(R.id.imageViewFavoriteMovie);
        descriptionTextView = findViewById(R.id.textViewDescription);
        trailersRecyclerView = findViewById(R.id.recyclerViewTrailers);
        reviewsRecyclerView = findViewById(R.id.recyclerViewReviews);
    }

    private void setUpRecyclerViews() {
        trailersAdapter = new TrailersAdapter();
        reviewsAdapter = new ReviewsAdapter();
        trailersRecyclerView.setAdapter(trailersAdapter);
        reviewsRecyclerView.setAdapter(reviewsAdapter);
    }

    public static Intent launchMovieDetailScreen(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE_EXTRA_NAME, movie);
        return intent;
    }
}