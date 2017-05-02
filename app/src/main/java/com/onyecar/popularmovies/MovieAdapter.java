package com.onyecar.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by onyekaanene on 28/04/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>  {
    private Movie[] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;
    Context context;
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie weatherForDay);
    }
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMovieImageView;
        public final TextView mWeatherTextView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = (ImageView) view.findViewById(R.id.img_movie_icon);
            mWeatherTextView = (TextView) view.findViewById(R.id.tv_movie_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movieInfo = mMovieData[adapterPosition];
            mClickHandler.onClick(movieInfo);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = mMovieData[position];
        String imageUrl = APIInfo.IMAGE_BASE_URL+APIInfo.IMAGE_SIZE+movie.getPosterPath();
        Picasso.with(context).load(imageUrl).into(holder.mMovieImageView);
    }
    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }
    public void setMovieData(Movie[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
