package com.nathansass.flicks.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.nathansass.flicks.R;
import com.nathansass.flicks.databinding.ActivityMovieDetailBinding;
import com.nathansass.flicks.models.Movie;
import com.nathansass.flicks.models.Movies;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailActivity extends AppCompatActivity {

    private ActivityMovieDetailBinding binding;

    Movie movie;
    Context context;

    int position;

    @BindView(R.id.ivMovieImage)
    ImageView movieImage;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_movie_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        position = getIntent().getIntExtra("position", -1);
        movie = Movies.get().getMovies().get(position);
        binding.setMovie(movie);

        context = this;

        updateUI();

    }

    public void updateUI() {
        Picasso.with(context).load(movie.getBackdropPath()).fit()
                .into(movieImage);

        tvTitle.setText( movie.getOriginalTitle() );

        tvOverview.setText( movie.getOverview() );
    }

    @OnClick(R.id.ivMovieImage)
    public void MainImageClick() {
        Intent i = new Intent(MovieDetailActivity.this, YoutubePlayer.class);
        i.putExtra("position", position);
        startActivity(i);
    }
}
