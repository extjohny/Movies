package com.example.movies.data;

import com.example.movies.data.movie.MoviesResponse;
import com.example.movies.data.review.ReviewsResponse;
import com.example.movies.data.trailer.TrailersResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String TOKEN = "Z8FJHNZ-Q1V4YEE-JR4MJXK-X3B1AXQ";

    @GET("movie?rating.kp=7-10&sortField=votes.kp&sortType=-1")
    @Headers("X-API-KEY: " + TOKEN)
    Single<MoviesResponse> loadMovies(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("movie/{id}")
    @Headers("X-API-KEY: " + TOKEN)
    Single<TrailersResponse> loadTrailers(@Path("id") int id);

    @GET("review?")
    @Headers("X-API-KEY: " + TOKEN)
    Single<ReviewsResponse> loadReviews(
            @Query("movieId") int movieId,
            @Query("page") int page);
}
