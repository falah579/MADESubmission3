package com.idn.falah.madesubmission.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.idn.falah.madesubmission.model.Favorite;

import java.util.ArrayList;

import static com.idn.falah.madesubmission.db.DatabaseContract.FavoriteColumns.MOVIE_DATE;
import static com.idn.falah.madesubmission.db.DatabaseContract.FavoriteColumns.MOVIE_DESCRIPTION;
import static com.idn.falah.madesubmission.db.DatabaseContract.FavoriteColumns.MOVIE_ELIMINATOR;
import static com.idn.falah.madesubmission.db.DatabaseContract.FavoriteColumns.MOVIE_ID;
import static com.idn.falah.madesubmission.db.DatabaseContract.FavoriteColumns.MOVIE_POSTER;
import static com.idn.falah.madesubmission.db.DatabaseContract.FavoriteColumns.MOVIE_RATING;
import static com.idn.falah.madesubmission.db.DatabaseContract.FavoriteColumns.MOVIE_TITLE;
import static com.idn.falah.madesubmission.db.DatabaseContract.FavoriteColumns._ID;
import static com.idn.falah.madesubmission.db.DatabaseContract.TABLE_FAVORITE;

public class FavHelper {
    private static final String DATABASE_TABLE = TABLE_FAVORITE;

    private static DatabaseHelper databaseHelper;
    private static FavHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }
    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }
    /* CRUD OPERATIONS */
    public ArrayList<Favorite> getAllFavs() {
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Log.d("run", "till here -3");
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        Log.d("run", "till here -2");
        cursor.moveToFirst();
        Favorite favorite;
        Log.d("run", "till here -1");
        if (cursor.getCount() > 0) {
            Log.d("run", "till here");
            do {
                Log.d("run", "till here2");
                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setMovieId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                favorite.setMovieTitle(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
                favorite.setMovieDescription(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCRIPTION)));
                favorite.setMoviePoster(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER)));
                favorite.setMovieDate(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DATE)));
                favorite.setMovieRating(cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_RATING)));
                favorite.setMovieEliminator(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_ELIMINATOR)));

                arrayList.add(favorite);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
            Log.d("run", "till here3");
        }
        Log.d("run", "till here4");
        cursor.close();
        return arrayList;
    }

    public long insertFav(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(MOVIE_ID, favorite.getMovieId());
        args.put(MOVIE_TITLE, favorite.getMovieTitle());
        args.put(MOVIE_DESCRIPTION, favorite.getMovieDescription());
        args.put(MOVIE_POSTER, favorite.getMoviePoster());
        args.put(MOVIE_RATING, favorite.getMovieRating());
        args.put(MOVIE_DATE, favorite.getMovieDate());
        args.put(MOVIE_ELIMINATOR, favorite.getMovieEliminator());

        Long result = database.insert(DATABASE_TABLE, null, args);


        Log.d("retro favhelperinsert", favorite.getMovieId() + ", title"+favorite.getMovieTitle() + " and result " + result);

        return result;
    }

    public int updateFav(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(MOVIE_ID, favorite.getMovieId());
        args.put(MOVIE_TITLE, favorite.getMovieTitle());
        args.put(MOVIE_DESCRIPTION, favorite.getMovieDescription());
        args.put(MOVIE_POSTER, favorite.getMoviePoster());
        args.put(MOVIE_RATING, favorite.getMovieRating());
        args.put(MOVIE_DATE, favorite.getMovieDate());
        args.put(MOVIE_ELIMINATOR, favorite.getMovieEliminator());

        return database.update(DATABASE_TABLE, args, _ID + "= '" + favorite.getId() + "'", null);
    }

    public int deletefav(int movieId) {
        return database.delete(TABLE_FAVORITE, MOVIE_ID + " = '" + movieId + "'", null);
    }
}
