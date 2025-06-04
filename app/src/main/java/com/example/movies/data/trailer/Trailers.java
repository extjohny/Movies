package com.example.movies.data.trailer;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {

    @SerializedName("trailers")
    private final List<Trailer> trailersList;

    public Trailers(List<Trailer> trailersList) {
        this.trailersList = trailersList;
    }

    public List<Trailer> getTrailersList() {
        return trailersList;
    }

    @NonNull
    @Override
    public String toString() {
        return "Trailers{" +
                "trailersList=" + trailersList +
                '}';
    }
}
