package com.idn.falah.madesubmission.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.idn.falah.madesubmission.Rest.ApiInterface;
import com.idn.farras.madesubmission.BuildConfig;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailViewModel extends ViewModel {

    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String API_KEY = BuildConfig.TMDB_API_KEY;

    private final String language = Locale.getDefault().getLanguage();

    private MutableLiveData<TvShowsItem> tvShowsLiveData = new MutableLiveData<>();
    private MutableLiveData<MoviesItem> moviesLiveData = new MutableLiveData<>();

    public void getMovieData(String movieId, final View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<MoviesItem> itemCall = apiInterface.getMovieItem("movie/"+movieId+"?api_key="+API_KEY+"&language="+language);
        itemCall.enqueue(new Callback<MoviesItem>() {
            @Override
            public void onResponse(@NonNull Call<MoviesItem> call, @NonNull Response<MoviesItem> response) {
                Log.d("Retrofit result", "code: "+response.code());
                MoviesItem moviesItem = response.body();

                if (response.code() != 200) {
                    Snackbar.make(view, response.message(), Snackbar.LENGTH_LONG);
                }
                    moviesLiveData.postValue(moviesItem);
            }

            @Override
            public void onFailure(@NonNull Call<MoviesItem> call, @NonNull Throwable t) {
                Log.d("Retrofit result", t.getMessage());
            }
        });
    }

    public void getTvData(String movieId, final View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<TvShowsItem> itemCall = apiInterface.getTvItem("tv/"+movieId+"?api_key="+API_KEY+"&language="+language);
        itemCall.enqueue(new Callback<TvShowsItem>() {
            @Override
            public void onResponse(@NonNull Call<TvShowsItem> call, @NonNull Response<TvShowsItem> response) {
                Log.d("Retrofit result", "code: "+response.code());
                TvShowsItem tvShowsItem = response.body();

                if (response.code() != 200) {
                    Snackbar.make(view, response.message(), Snackbar.LENGTH_LONG);
                }
                    tvShowsLiveData.postValue(tvShowsItem);
            }

            @Override
            public void onFailure(@NonNull Call<TvShowsItem> call, @NonNull Throwable t) {
                Log.d("Retrofit result", t.getMessage());
            }
        });
    }

    public LiveData<MoviesItem> getMovies() {
        return moviesLiveData;
    }

    public LiveData<TvShowsItem> getTvShows() {
        return tvShowsLiveData;
    }
}
