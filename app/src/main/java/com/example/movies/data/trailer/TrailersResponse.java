package com.example.movies.data.trailer;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class TrailersResponse {

    @SerializedName("videos")
    private final Trailers trailers;

    public TrailersResponse(Trailers trailers) {
        this.trailers = trailers;
    }

    public Trailers getTrailers() {
        return trailers;
    }

    @NonNull
    @Override
    public String toString() {
        return "TrailersResponse{" +
                "trailers=" + trailers +
                '}';
    }
}
