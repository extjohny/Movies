package com.example.movies.data.review;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewsResponse {

    @SerializedName("docs")
    private final List<Review> reviews;

    public ReviewsResponse(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @NonNull
    @Override
    public String toString() {
        return "Reviews{" +
                "reviews=" + reviews +
                '}';
    }
}
