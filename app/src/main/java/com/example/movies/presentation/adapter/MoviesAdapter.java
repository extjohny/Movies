package com.example.movies.presentation.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.data.movie.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    private OnReachEndListener onReachEndListener;
    private OnMovieClickListener onMovieClickListener;

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.movie_item,
                        parent,
                        false
                );
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
        if (position >= getItemCount() - 10 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        holder.movieImageView.setOnClickListener(
                view -> {
                    if (onMovieClickListener != null) {
                        onMovieClickListener.onMovieCLick(movies.get(position));
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        }
        return movies.size();
    }

    protected static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView movieImageView;
        private final TextView ratingTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.imageViewMovie);
            ratingTextView = itemView.findViewById(R.id.textViewRating);
        }

        private void bind(Movie movie) {
            String url = movie.getPoster().getUrl();
            double rating = movie.getRating().getKinopoisk();

            Glide.with(itemView.getContext())
                    .load(url)
                    .error(R.drawable.network_locked)
                    .into(movieImageView);

            int backgroundId;
            if (rating > 7) backgroundId = R.drawable.cirle_green;
            else if (rating < 7 || rating > 5) backgroundId = R.drawable.cirle_orange;
            else backgroundId = R.drawable.cirle_red;
            Drawable color = ContextCompat.getDrawable(itemView.getContext(), backgroundId);

            try {
                ratingTextView.setText(String.valueOf(rating).substring(0, 3));
                ratingTextView.setBackground(color);
            } catch (IllegalArgumentException e) {
                ratingTextView.setText(R.string.network_problem);
            }
        }
    }

    public interface OnReachEndListener {

        void onReachEnd();
    }

    public interface OnMovieClickListener {

        void onMovieCLick(Movie movie);
    }
}
