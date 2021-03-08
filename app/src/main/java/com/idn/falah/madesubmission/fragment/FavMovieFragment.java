package com.idn.falah.madesubmission.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.idn.falah.madesubmission.adapter.MovieAdapter;
import com.idn.falah.madesubmission.db.FavHelper;
import com.idn.falah.madesubmission.model.Favorite;
import com.idn.farras.madesubmission.R;
import com.idn.falah.madesubmission.model.FavViewModel;
import com.idn.falah.madesubmission.model.MoviesItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private MovieAdapter adapter;
    private FavViewModel viewModel;

    private FavHelper favHelper;

    public FavMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        favHelper = FavHelper.getInstance(getActivity());
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FavViewModel.class);
        viewModel.getListsMovies().observe(this, getData);

        favHelper.open();
        ArrayList<Favorite> favData = new ArrayList<>(favHelper.getAllFavs());
        viewModel.setListTvShows(favData);
        viewModel.setListMovies(favData);

        recyclerView = view.findViewById(R.id.list_film);
        progressBar = view.findViewById(R.id.progress_film);

        adapter = new MovieAdapter(getContext());

        recyclerSetup();
        showLoading(true);
    }
    private Observer<ArrayList<MoviesItem>> getData = new Observer<ArrayList<MoviesItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MoviesItem> moviesItems) {
            adapter.setList(moviesItems);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

            showLoading(false);
        }
    };

    private void recyclerSetup() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showLoading(Boolean bool) {
        if (bool) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
