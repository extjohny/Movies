package com.example.movies.data.movie;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    @SerializedName("kp")
    private final double kinopoisk;

    public Rating(double kinopoisk) {
        this.kinopoisk = kinopoisk;
    }

    public double getKinopoisk() {
        return kinopoisk;
    }

    @NonNull
    @Override
    public String toString() {
        return "Rating{" +
                "kinopoisk='" + kinopoisk + '\'' +
                '}';
    }
}
