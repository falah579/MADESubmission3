package com.idn.falah.madesubmission.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.idn.falah.madesubmission.db.DatabaseContract.TABLE_FAVORITE;
import com.idn.falah.madesubmission.db.DatabaseContract.FavoriteColumns;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbfavmovie";

    private static final int DATABASE_VERSION = 2;
    // TODO coba id ke int
    private static final String SQL_CREATE_TABLE_FAV = String.format("CREATE TABLE %s"
                + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL);",
            TABLE_FAVORITE,
            FavoriteColumns._ID,
            FavoriteColumns.MOVIE_ID,
            FavoriteColumns.MOVIE_TITLE,
            FavoriteColumns.MOVIE_DESCRIPTION,
            FavoriteColumns.MOVIE_POSTER,
            FavoriteColumns.MOVIE_DATE,
            FavoriteColumns.MOVIE_RATING,
            FavoriteColumns.MOVIE_ELIMINATOR
            );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }
}
