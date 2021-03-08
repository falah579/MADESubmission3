package com.idn.falah.madesubmission;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.idn.falah.madesubmission.db.DatabaseContract;
import com.idn.falah.madesubmission.db.FavHelper;
import com.idn.falah.madesubmission.model.Favorite;
import com.idn.farras.madesubmission.R;
import com.idn.falah.madesubmission.model.DetailViewModel;
import com.idn.falah.madesubmission.model.MoviesItem;
import com.idn.falah.madesubmission.model.TvShowsItem;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDetailTitle, txtDetailDescription, txtDetailRelease, txtDetailRating;
    private ProgressBar progressBar;
    private ImageView imgDetailPoster;
    private Button fabFavorite;
    private View detailContainer;

    public static final String TAG_KEY= "tag";
    public static final String MOVIE_ID_KEY= "movie_id";

    private final String imageUrl = "https://image.tmdb.org/t/p/w185";

    private Favorite favorite;

    private FavHelper favHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        favHelper = FavHelper.getInstance(getApplicationContext());
        favorite = new Favorite();

        DetailViewModel detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        txtDetailTitle = findViewById(R.id.txt_detail_judul);
        txtDetailDescription = findViewById(R.id.txt_detail_deskripsi);
        txtDetailRelease = findViewById(R.id.txt_detail_tanggal_rilis);
        txtDetailRating = findViewById(R.id.txt_detail_rating);

        imgDetailPoster = findViewById(R.id.img_detail_foto);
        progressBar = findViewById(R.id.detail_progress);
        fabFavorite = findViewById(R.id.fab_favorite_detail);
        detailContainer = findViewById(R.id.detail_container);

        String tag = getIntent().getStringExtra(TAG_KEY);
        String movieId = getIntent().getStringExtra(MOVIE_ID_KEY);

        if (tag.equals("movie")) {
            detailViewModel.getMovies().observe(this, getMovie);
            detailViewModel.getMovieData(movieId, detailContainer);
        } else {
            detailViewModel.getTvShows().observe(this, getTv);
            detailViewModel.getTvData(movieId, detailContainer);
        }

        showLoading(true);

        fabFavorite.setOnClickListener(this);

    }

    private Observer<MoviesItem> getMovie = new Observer<MoviesItem>() {
        @Override
        public void onChanged(@Nullable MoviesItem moviesItem) {
            if (moviesItem != null) {


                txtDetailTitle.setText(moviesItem.getTitle());
                txtDetailDescription.setText(moviesItem.getOverview());
                txtDetailRelease.setText(moviesItem.getReleaseDate());
                txtDetailRating.setText(String.valueOf(moviesItem.getVoteAverage()));

                favorite.setMovieId(moviesItem.getId());
                favorite.setMovieTitle(moviesItem.getTitle());
                favorite.setMovieDescription(moviesItem.getOverview());
                favorite.setMoviePoster(moviesItem.getPosterPath());
                favorite.setMovieRating(moviesItem.getVoteAverage());
                favorite.setMovieDate(moviesItem.getReleaseDate());
                favorite.setMovieEliminator("movie");

                Glide.with(getApplicationContext()).load(imageUrl+moviesItem.getPosterPath()).into(imgDetailPoster);

                if (moviesItem.getOverview().equalsIgnoreCase("") || moviesItem.getOverview() == null || moviesItem.getOverview().isEmpty()) {
                    txtDetailDescription.setText(getResources().getText(R.string.empty_description));
                }

                showLoading(false);
            }
        }
    };

    private Observer<TvShowsItem> getTv = new Observer<TvShowsItem>() {
        @Override
        public void onChanged(@Nullable TvShowsItem tvShowsItem) {
            if (tvShowsItem != null) {

                txtDetailTitle.setText(tvShowsItem.getName());
                txtDetailDescription.setText(tvShowsItem.getOverview());
                txtDetailRelease.setText(tvShowsItem.getFirstAirDate());
                txtDetailRating.setText(String.valueOf(tvShowsItem.getVoteAverage()));

                favorite.setMovieId(tvShowsItem.getId());
                favorite.setMovieTitle(tvShowsItem.getName());
                favorite.setMovieDescription(tvShowsItem.getOverview());
                favorite.setMoviePoster(tvShowsItem.getPosterPath());
                favorite.setMovieRating(tvShowsItem.getVoteAverage());
                favorite.setMovieDate(tvShowsItem.getFirstAirDate());
                favorite.setMovieEliminator("tvshows");

                Glide.with(getApplicationContext()).load(imageUrl+tvShowsItem.getPosterPath()).into(imgDetailPoster);
                showLoading(false);

                if (tvShowsItem.getOverview().equalsIgnoreCase("") || tvShowsItem.getOverview() == null || tvShowsItem.getOverview().isEmpty()) {
                    txtDetailDescription.setText(R.string.empty_description);
                }
            }
        }
    };

    private void showLoading(Boolean bool) {
        if (bool) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_favorite_detail:
                favHelper.open();
                ArrayList<Favorite> favData = new ArrayList<>(favHelper.getAllFavs());
                ArrayList<Integer> listMovieId = new ArrayList<>();

                long result;

                for (int i=0; i < favData.size();i++) {
                    listMovieId.add(favData.get(i).getMovieId());
                }
                if (listMovieId.contains(favorite.getMovieId())) {
                    favHelper.deletefav(favorite.getMovieId());
                    Snackbar.make(detailContainer, "Deleted from favorite", Snackbar.LENGTH_LONG);
                    Toast.makeText(this, "Deleted from favorite", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("retrofit bukan", "movieId"+favorite.getMovieId()+", id"+favorite.getId()+", title"+favorite.getMovieTitle());
                    result = favHelper.insertFav(favorite);

                    if (result > 0) {
                        favorite.setId((int) result);
                        Toast.makeText(DetailActivity.this, "Added favorite " + result, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "Failed to add data " + result, Toast.LENGTH_SHORT).show();
                    }
                }

                favHelper.close();
                break;
        }
    }

    public boolean checkIsFavorite() {
        boolean isFavorite = false;

        String URI_AUTHORITY = "idn.farras.madesubmission";
        Uri uri = new Uri.Builder().scheme("content")
                .authority(URI_AUTHORITY)
                .appendPath(DatabaseContract.TABLE_FAVORITE)
                .build();

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int movieId;
        if (cursor.moveToFirst()) {
            do {
                movieId = cursor.getInt(1);
                if (movieId == favorite.getMovieId()) {
                    isFavorite = true;
                }
            } while (cursor.moveToNext());
        }
//        cursor.close();
        return isFavorite;
    }
}
