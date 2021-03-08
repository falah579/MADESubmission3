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

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private  final String BASE_URL = "https://api.themoviedb.org/3/discover/";
    private final String API_KEY = BuildConfig.TMDB_API_KEY;

    private final String language = ""+ Locale.getDefault().getLanguage();

    private MutableLiveData<ArrayList<TvShowsItem>> listMutableTvShows = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MoviesItem>> listMutableMovies = new MutableLiveData<>();

    public void setListTvShows(final View view) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ResponseTvShows> tvShowsCall = apiInterface.getTvShows("tv?api_key="+API_KEY+"&language="+language);
        tvShowsCall.enqueue(new Callback<ResponseTvShows>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTvShows> call, @NonNull Response<ResponseTvShows> response) {
                Log.d("Retrofit result", "code: "+response.code());

                if (response.code() != 200) {
                    Snackbar.make(view, response.message(), Snackbar.LENGTH_LONG);
                }


                if (response.body() != null) {
                    ArrayList<TvShowsItem> tvShowsItemList = response.body().getResults();
                    listMutableTvShows.postValue(tvShowsItemList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTvShows> call, @NonNull Throwable t) {
                Log.e("Retrofit result", t.toString());
            }
        });
    }

    public void setListMovies(final View view) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ResponseMovies> tvShowsCall = apiInterface.getMovies("movie?api_key="+API_KEY+"&language="+language);
        tvShowsCall.enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMovies> call, @NonNull Response<ResponseMovies> response) {
                Log.d("Retrofit result", "code: "+response.code());

                if (response.code() != 200) {
                    Snackbar.make(view, response.message(), Snackbar.LENGTH_LONG);
                }

                if (response.body() != null) {
                    ArrayList<MoviesItem> moviesItemList = response.body().getResults();
                    listMutableMovies.postValue(moviesItemList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMovies> call, @NonNull Throwable t) {
                Log.e("Retrofit Result", t.toString());
            }
        });
    }

    public LiveData<ArrayList<TvShowsItem>> getListsTvShows() {
        return listMutableTvShows;
    }

    public LiveData<ArrayList<MoviesItem>> getListsMovies() {
        return listMutableMovies;
    }
}
