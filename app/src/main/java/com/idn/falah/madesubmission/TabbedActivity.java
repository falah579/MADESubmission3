package com.idn.falah.madesubmission;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.idn.farras.madesubmission.R;
import com.idn.falah.madesubmission.adapter.SimpleFragmentPagerAdapter;


public class TabbedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        ViewPager pagerGenre = findViewById(R.id.pager_genre);
        TabLayout tabGenre = findViewById(R.id.tab_genre);

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        pagerGenre.setAdapter(adapter);

        tabGenre.setupWithViewPager(pagerGenre);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_change_language:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                break;
            case R.id.menu_item_favorite:
                startActivity(new Intent(this, FavoriteActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
