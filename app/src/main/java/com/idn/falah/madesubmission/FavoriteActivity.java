package com.idn.falah.madesubmission;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idn.farras.madesubmission.R;
import com.idn.falah.madesubmission.adapter.FavoritePagerAdapter;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ViewPager viewPager = findViewById(R.id.v_pager_favorite);
        TabLayout tabLayout = findViewById(R.id.tab_favorite);

        FavoritePagerAdapter adapter = new FavoritePagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
