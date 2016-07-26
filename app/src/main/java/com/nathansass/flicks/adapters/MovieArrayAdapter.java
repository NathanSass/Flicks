package com.nathansass.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nathansass.flicks.R;
import com.nathansass.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by nathansass on 7/18/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public static class ViewHolder {

        @Nullable @BindView(R.id.tvTitle)
        TextView tvTitle;
        @Nullable @BindView(R.id.tvOverview)
        TextView tvOverview;
        @Nullable @BindView(R.id.ivMovieImage)
        ImageView ivImage;
        @Nullable @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @Nullable @BindView(R.id.bgGradient)
        View bgGradient;
        public ViewHolder (View view) {
            ButterKnife.bind(this, view);
        }

    }
    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) { // how does it know the order
        Movie movie = getItem(position);
        int popularity = movie.getPopularity();

        if (popularity >= 13) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);


        final ViewHolder viewHolder;

        int moviePopularity = movie.getPopularity();
        if (convertView == null) {

            convertView = getInflatedLayoutForPopularity(moviePopularity, parent);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String movieImage = "";
        int movieImagePlaceholder = 0;

        int orientation = getContext().getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT && moviePopularity < 13) {
            movieImage = movie.getPosterPath();
            movieImagePlaceholder = R.drawable.item_movie_p;
        } else { // if (orientation == Configuration.ORIENTATION_LANDSCAPE || moviePopularity >= 13
            movieImage = movie.getBackdropPath();
            movieImagePlaceholder = R.drawable.item_movie_l;
        }

        Picasso.with(getContext()).load(movieImage).fit()
                .transform(new RoundedCornersTransformation(2,2))
                .placeholder(movieImagePlaceholder)
                .into(viewHolder.ivImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (viewHolder.bgGradient != null) {
                            viewHolder.bgGradient.getLayoutParams().height = viewHolder.ivImage.getMeasuredHeight();
//                            viewHolder.bgGradient.invalidate();
//                            viewHolder.bgGradient.requestLayout();
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

        if (viewHolder.tvTitle != null) { // what is the best way to do checks for each of these

            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            viewHolder.ratingBar.setRating(movie.getRating());
            viewHolder.tvOverview.setText(movie.getOverview());
        }

        return convertView;
    }

    private View getInflatedLayoutForPopularity(int popularity, ViewGroup parent) {

        if (popularity >= 13) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_preview, parent, false);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }
    }
}
