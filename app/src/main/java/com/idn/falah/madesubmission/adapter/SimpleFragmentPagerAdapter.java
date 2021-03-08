package com.idn.falah.madesubmission.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.idn.falah.madesubmission.fragment.MoviesFragment;
import com.idn.falah.madesubmission.fragment.TvShowsFragment;
import com.idn.farras.madesubmission.R;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context aContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        aContext = context;
    }

    // Determines fragment for each tab
    @Override
    public Fragment getItem(int i) {
        if (i == 1) {
            return new TvShowsFragment();
        } else {
            return new MoviesFragment();
        }
    }

    // determine number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // determine title of each tabs
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 1) {
            return aContext.getString(R.string.tab_category_tvshows);
        } else {
            return aContext.getString(R.string.tab_category_movies);
        }

//        return super.getPageTitle(position);
    }
}
