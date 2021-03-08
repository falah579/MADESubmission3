package com.idn.falah.madesubmission.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_FAVORITE = "favorite";

    static final class FavoriteColumns implements BaseColumns {
        static String MOVIE_ID = "movie_id";
        static String MOVIE_TITLE = "movie_title";
        static String MOVIE_DESCRIPTION = "movie_description";
        static String MOVIE_POSTER = "movie_poster";
        static String MOVIE_DATE = "movie_date";
        static String MOVIE_RATING = "movie_rating";
        static String MOVIE_ELIMINATOR = "movie_eliminator";
    }

}
