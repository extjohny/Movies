package com.example.movies.presentation.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.data.ApiFactory;
import com.example.movies.data.MoviesDao;
import com.example.movies.data.MoviesDatabase;
import com.example.movies.data.movie.Movie;
import com.example.movies.data.review.Review;
import com.example.movies.data.review.ReviewsResponse;
import com.example.movies.data.trailer.Trailer;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private static final String TAG = "MovieDetailViewModel";

    private final MoviesDao moviesDao;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private int reviewsPage = 1;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MoviesDatabase.getInstance(application).moviesDao();
    }

    public LiveData<Movie> getFavoriteMovie(int movieId) {
        return moviesDao.getFavoriteMovie(movieId);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public void addMovieToFavorite(Movie movie) {
        Disposable disposable = moviesDao.addMovieToFavorites(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void removeMovieFromFavorite(int movieId) {
        Disposable disposable = moviesDao.removeMovieFromFavorites(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.getApiService().loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(trailersResponse ->
                        trailersResponse.getTrailers().getTrailersList())
                .subscribe(
                        trailers::setValue,
                        throwable -> Log.d(TAG, throwable.toString())
                );
        compositeDisposable.add(disposable);
    }

    public void loadReviews(int movieId) {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        Disposable disposable = ApiFactory.getApiService().loadReviews(movieId, reviewsPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ReviewsResponse::getReviews)
                .doOnTerminate(() -> isLoading.setValue(false))
                .subscribe(
                        reviewsFromNetwork -> {
                            List<Review> temp = reviews.getValue();
                            if (temp == null) {
                                reviews.setValue(reviewsFromNetwork);
                            } else {
                                temp.addAll(reviewsFromNetwork);
                            }
                            isLoading.setValue(true);
                            reviewsPage++;
                        },
                        throwable -> Log.d(TAG, throwable.toString())
                );
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
