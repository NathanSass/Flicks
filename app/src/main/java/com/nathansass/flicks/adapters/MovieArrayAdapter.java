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

//    @BindView(R.id.ivMovieImage) ImageView ivImage;

    public static class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivImage;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);


        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent,false);

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.ivImage.setImageResource(0);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



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
                .into(viewHolder.ivImage);

        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        return convertView;
    }
}
