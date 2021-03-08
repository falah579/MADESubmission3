package com.idn.falah.madesubmission.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class FavViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TvShowsItem>> listMutableTvShows = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MoviesItem>> listMutableMovies = new MutableLiveData<>();

    public void setListTvShows(ArrayList<Favorite> favorites) {
        ArrayList<TvShowsItem> tvShowsItemList = new ArrayList<>();

        TvShowsItem item;
        for (int i = 0; i < favorites.size(); i++) {

            if (favorites.get(i).getMovieEliminator().equals("tvshows")) {
                item = new TvShowsItem();
                item.setId(favorites.get(i).getMovieId());
                item.setName(favorites.get(i).getMovieTitle());
                item.setOverview(favorites.get(i).getMovieDescription());
                item.setFirstAirDate(favorites.get(i).getMovieDate());
                item.setVoteAverage(favorites.get(i).getMovieRating());
                item.setPosterPath(favorites.get(i).getMoviePoster());

                tvShowsItemList.add(item);
            }
        }

        listMutableTvShows.postValue(tvShowsItemList);
    }

    public void setListMovies(ArrayList<Favorite> favorites) {
        ArrayList<MoviesItem> moviesItemList = new ArrayList<>();

        MoviesItem item;
        for (int i = 0; i < favorites.size(); i++) {

            if (favorites.get(i).getMovieEliminator().equals("movie")) {
                item = new MoviesItem();
                item.setId(favorites.get(i).getMovieId());
                item.setTitle(favorites.get(i).getMovieTitle());
                item.setOverview(favorites.get(i).getMovieDescription());
                item.setReleaseDate(favorites.get(i).getMovieDate());
                item.setVoteAverage(favorites.get(i).getMovieRating());
                item.setPosterPath(favorites.get(i).getMoviePoster());

                moviesItemList.add(item);
            }

        }

        listMutableMovies.postValue(moviesItemList);
    }

    public LiveData<ArrayList<TvShowsItem>> getListsTvShows() {
        return listMutableTvShows;
    }

    public LiveData<ArrayList<MoviesItem>> getListsMovies() {
        return listMutableMovies;
    }
}
