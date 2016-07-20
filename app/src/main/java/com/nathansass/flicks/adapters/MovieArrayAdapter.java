package com.nathansass.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nathansass.flicks.R;
import com.nathansass.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nathansass on 7/18/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie,parent,false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);

        ivImage.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);

        String movieImage = "";
        int movieImagePlaceholder = 0;

        int orientation = getContext().getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            movieImage = movie.getPosterPath();
            movieImagePlaceholder = R.drawable.item_movie_p;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            movieImage = movie.getBackdropPath();
            movieImagePlaceholder = R.drawable.item_movie_l;
        }

        Picasso.with(getContext()).load(movieImage).fit()
                .placeholder(movieImagePlaceholder)
                .into(ivImage);

        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());

        return convertView;
    }
}
