package com.nathansass.flicks.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nathansass.flicks.R;
import com.nathansass.flicks.models.Movie;
import com.nathansass.flicks.models.Movies;

public class MovieDetail extends AppCompatActivity {

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
//        setTitle("Tarzan: The new Begininnig");

        /* Inside the activity */
// Sets the Toolbar to act as the ActionBar for this Activity window.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view

        int position = getIntent().getIntExtra("position", -1);
        movie = Movies.get().getMovies().get(position);

    }
}
