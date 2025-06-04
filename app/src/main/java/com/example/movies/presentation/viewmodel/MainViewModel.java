package com.example.movies.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.data.ApiFactory;
import com.example.movies.data.movie.Movie;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private static final int limit = 20;
    private int page = 1;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadMovies() {
        if (Boolean.TRUE.equals(isLoading.getValue())) {
            return;
        }
        Disposable disposable = ApiFactory.getApiService().loadMovies(page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> isLoading.setValue(true))
                .doOnTerminate(() -> isLoading.setValue(false))
                .subscribe(
                        moviesFromNetwork -> {
                            List<Movie> temp = movies.getValue();
                            if (temp == null) {
                                temp = moviesFromNetwork.getMovies();
                            } else {
                                temp.addAll(moviesFromNetwork.getMovies());
                            }
                            movies.setValue(temp);
                            page++;
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
