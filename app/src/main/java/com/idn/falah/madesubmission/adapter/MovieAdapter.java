package com.idn.falah.madesubmission.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idn.falah.madesubmission.DetailActivity;
import com.idn.falah.madesubmission.model.Favorite;
import com.idn.farras.madesubmission.R;
import com.idn.falah.madesubmission.model.MoviesItem;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    final private Context mContext;
    private ArrayList<MoviesItem> mList;
    private ArrayList<Favorite> favList;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private ArrayList<MoviesItem> getList() {
        return mList;
    }

    public void setList(ArrayList<MoviesItem> mList) {
        if (this.mList != null && !this.mList.isEmpty()) {
            this.mList.clear();
        }
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addToFav(Favorite favorite) {
        this.favList.add(favorite);
        notifyItemInserted(favList.size() - 1);
    }

    public void updateFavItem(int position, Favorite favorite) {
        this.favList.set(position, favorite);
        notifyItemChanged(position, favorite);
    }

    public void removeFromFav(int position) {
        this.favList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favList.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(getList().get(i));
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        final private TextView txtTitle, txtDescription;
        final private ImageView imgPoster;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_judul);
            txtDescription = itemView.findViewById(R.id.txt_deskripsi);
            imgPoster = itemView.findViewById(R.id.img_foto);
        }

        void bind(final MoviesItem moviesItem) {
            txtTitle.setText(moviesItem.getTitle());
            txtDescription.setText(moviesItem.getOverview());
            Glide.with(mContext).load("https://image.tmdb.org/t/p/w185/"+moviesItem.getPosterPath()).into(imgPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, DetailActivity.class)
                            .putExtra(DetailActivity.TAG_KEY, "movie")
                            .putExtra(DetailActivity.MOVIE_ID_KEY, moviesItem.getId()+""));
                }
            });

            if (moviesItem.getOverview() == null || moviesItem.getOverview().isEmpty() || moviesItem.getOverview().equalsIgnoreCase("")) {
                txtDescription.setText(R.string.empty_description);
            }
        }
    }
}
