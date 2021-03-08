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
import com.idn.falah.madesubmission.model.TvShowsItem;
import com.idn.farras.madesubmission.R;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.MyViewHolder> {

    final private Context mContext;
    private ArrayList<TvShowsItem> mList;

    public TvShowAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private ArrayList<TvShowsItem> getList() {
        return mList;
    }

    public void setList(ArrayList<TvShowsItem> mList) {
        this.mList = mList;
        notifyDataSetChanged();
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

        void bind(final TvShowsItem tvShowsItem) {
            txtTitle.setText(tvShowsItem.getName());
            txtDescription.setText(tvShowsItem.getOverview());
            Glide.with(mContext).load("https://image.tmdb.org/t/p/w185/"+tvShowsItem.getPosterPath()).into(imgPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, DetailActivity.class)
                            .putExtra(DetailActivity.TAG_KEY, "tv")
                            .putExtra(DetailActivity.MOVIE_ID_KEY, tvShowsItem.getId()+""));
                }
            });

            if (tvShowsItem.getOverview() == null || tvShowsItem.getOverview().isEmpty() || tvShowsItem.getOverview().equalsIgnoreCase("")) {
                txtDescription.setText(R.string.empty_description);
            }
        }
    }
}
