package com.idn.falah.madesubmission.Rest;


import com.idn.falah.madesubmission.model.ResponseTvShows;
import com.idn.falah.madesubmission.model.MoviesItem;
import com.idn.falah.madesubmission.model.ResponseMovies;
import com.idn.falah.madesubmission.model.TvShowsItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET()
    Call<ResponseMovies> getMovies(@Url String url);

    @GET
    Call<ResponseTvShows> getTvShows(@Url String url);

    @GET
    Call<MoviesItem> getMovieItem(@Url String url);

    @GET
    Call<TvShowsItem> getTvItem(@Url String url);

}
