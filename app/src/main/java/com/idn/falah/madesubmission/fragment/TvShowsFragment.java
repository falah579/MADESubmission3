package com.idn.falah.madesubmission.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.idn.farras.madesubmission.R;
import com.idn.falah.madesubmission.adapter.TvShowAdapter;
import com.idn.falah.madesubmission.model.MainViewModel;
import com.idn.falah.madesubmission.model.TvShowsItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TvShowAdapter adapter;

    private SwipeRefreshLayout refreshLayout;
    private MainViewModel mainViewModel;

    public TvShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getListsTvShows().observe(this, getData);
        mainViewModel.setListTvShows(view);

        recyclerView = view.findViewById(R.id.list_film);
        progressBar = view.findViewById(R.id.progress_film);
        refreshLayout = view.findViewById(R.id.refresh_film);

        adapter = new TvShowAdapter(getContext());

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                mainViewModel.getListsTvShows().observe(TvShowsFragment.this, getData);
                mainViewModel.setListTvShows(view);
            }
        });

        recyclerSetup();
        showLoading(true);
    }

    private Observer<ArrayList<TvShowsItem>> getData = new Observer<ArrayList<TvShowsItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShowsItem> tvShowsItems) {
            if (tvShowsItems != null) {
                adapter.setList(tvShowsItems);
                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);
                showLoading(false);
                refreshLayout.setRefreshing(false);
            }
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
