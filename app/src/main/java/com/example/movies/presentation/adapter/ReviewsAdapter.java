package com.example.movies.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.data.review.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private static final String POSITIVE_REVIEW_TYPE = "Позитивный";
    private static final String NEUTRAL_REVIEW_TYPE = "Нейтральный";

    private List<Review> reviews = new ArrayList<>();

    private MoviesAdapter.OnReachEndListener onReachEndListener;

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public void setOnReachEndListener(MoviesAdapter.OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.review_item,
                        parent,
                        false
                );
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);

        int colorId;
        if (review.getType().equals(POSITIVE_REVIEW_TYPE)) {
            colorId = android.R.color.holo_green_light;
        } else if (review.getReview().equals(NEUTRAL_REVIEW_TYPE)) {
            colorId = android.R.color.holo_orange_light;
        } else {
            colorId = android.R.color.holo_red_light;
        }

        int color = ContextCompat.getColor(holder.itemView.getContext(), colorId);
        holder.authorTextView.setText(review.getAuthor());
        holder.reviewTextView.setText(review.getReview());
        holder.itemView.setBackgroundColor(color);

        if (position >= getItemCount() - 3 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView authorTextView;
        TextView reviewTextView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.textViewReviewAuthor);
            reviewTextView = itemView.findViewById(R.id.textViewReview);
        }
    }

    public interface onReachEndReviews {

        void onReachEnd();
    }
}
