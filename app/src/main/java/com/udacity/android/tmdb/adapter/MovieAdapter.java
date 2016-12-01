package com.udacity.android.tmdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.android.tmdb.R;
import com.udacity.android.tmdb.pojo.Movie;
import com.udacity.android.tmdb.remote.ApiClient;
import com.udacity.android.tmdb.ui.DetailActivity;

import java.util.List;

/**
 * Created by geovani on 11/30/16.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private Context mContext;
    private List<Movie> mMovies;


    public MovieAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovies  = movies;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shot, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.mTitleView.setText(movie.getOriginalTitle());
        holder.mLikeText.setText(String.valueOf(movie.getVoteAverage()));
        Picasso.with(mContext)
                .load(ApiClient.BASE_IMAGE + movie.getPosterPath())
//                .resize(200, 200)
//                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.mShotImage);
        startActivityOnClick(holder, movie);
    }


    private void startActivityOnClick(MyViewHolder holder, final Movie movie) {
        holder.mShotImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(EXTRA_MOVIE, movie);
                context.startActivity(intent);
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }



    protected class MyViewHolder extends RecyclerView.ViewHolder
    {
        public View mView;
        public TextView mTitleView;
        public TextView mLikeText;
        public ImageView mShotImage;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            mView       = itemView;
            mTitleView  = (TextView) itemView.findViewById(R.id.titleText);
            mLikeText   = (TextView) itemView.findViewById(R.id.likeText);
            mShotImage  = (ImageView) itemView.findViewById(R.id.shotImage);
        }
    }



}
