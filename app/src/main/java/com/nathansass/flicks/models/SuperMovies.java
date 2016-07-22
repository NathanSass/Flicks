package com.nathansass.flicks.models;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nathansass.flicks.interfaces.GetMoviesCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by nathansass on 7/20/16.
 */
public class SuperMovies {
    public static final String TAG = SuperMovies.class.getSimpleName();

    private ArrayList<Movie> movies = null;

    SuperMovies() {
        movies = new ArrayList<>();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void fetchMoviesAsync(final GetMoviesCallback callback) {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&append_to_response=trailer";
//        https://api.themoviedb.org/3/movie/550?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&append_to_response=releases,trailers

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callback.done(movieJsonResults);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

}

