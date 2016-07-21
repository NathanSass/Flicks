package com.nathansass.flicks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nathansass.flicks.adapters.MovieArrayAdapter;
import com.nathansass.flicks.interfaces.GetMoviesCallback;
import com.nathansass.flicks.models.Movie;
import com.nathansass.flicks.models.Movies;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

//    ArrayList<Movie> movies;
    Movies movies;
    MovieArrayAdapter movieAdapter;
    Context context;

    @BindView(R.id.lvMovies) ListView lvItems;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;

        movies = Movies.get();
        movieAdapter = new MovieArrayAdapter(this, Movies.get().getMovies());
        lvItems.setAdapter(movieAdapter);

        fetchMoviesAsync();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMoviesAsync();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "You win!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchMoviesAsync() {
        Movies.get().fetchMoviesAsync(new GetMoviesCallback() {
            @Override
            public void done(JSONArray movieJsonResults) {
                movieAdapter.clear();

                Movies.get().getMovies().addAll( Movie.fromJSONArray(movieJsonResults) );

                movieAdapter.notifyDataSetChanged();

                swipeContainer.setRefreshing(false);
            }
        });
    }

}
