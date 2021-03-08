package com.idn.falah.madesubmission.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.idn.falah.madesubmission.fragment.FavMovieFragment;
import com.idn.falah.madesubmission.fragment.FavTvFragment;
import com.idn.farras.madesubmission.R;

public class FavoritePagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public FavoritePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 1) {
            return new FavTvFragment();
        } else {
            return new FavMovieFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 1) {
            return context.getString(R.string.tab_category_tvshows);
        } else {
            return context.getString(R.string.tab_category_movies);
        }
    }
}
