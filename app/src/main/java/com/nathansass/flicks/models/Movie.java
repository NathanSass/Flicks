package com.nathansass.flicks.models;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nathansass.flicks.interfaces.GetTrailerUrlCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by nathansass on 7/18/16.
 */
public class Movie {
    String posterPath;

    String backdropPath;

    String originalTitle;

    String overview;

    Double rating;

    String trailerVideoKey;

    int popularity;

    int movieId;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = jsonObject.getDouble("vote_average");
        this.popularity = jsonObject.getInt("popularity");
        this.movieId = jsonObject.getInt("id");
        this.trailerVideoKey = "dQw4w9WgXcQ";

    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                final Movie movie = new Movie(array.getJSONObject(x));

                fetchTrailerUrlAsync(movie, new GetTrailerUrlCallback() {
                    @Override
                    public void done(String trailerUrl) {
                        movie.trailerVideoKey = trailerUrl;
                    }
                });

                results.add( movie );
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return results;
    }

    public static void fetchTrailerUrlAsync(Movie movie, final GetTrailerUrlCallback callback) {
        String url = " https://api.themoviedb.org/3/movie/" + movie.getMovieId() + "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&append_to_response=trailers";

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String trailerVideoKey = "dQw4w9WgXcQ";
                try {
                    JSONObject movieJsonResults = response.getJSONObject("trailers");
                    JSONArray trailers = movieJsonResults.getJSONArray("youtube");

                    if (trailers.length() > 0) {
                        JSONObject trailer = (JSONObject) trailers.get(0);
                        trailerVideoKey = trailer.getString("source");
//                        Log.v("DEBUG", "vidKey: " + trailerVideoKey);
                    }
                    callback.done(trailerVideoKey);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    public String getTrailerVideoKey() {
        return trailerVideoKey;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }
    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Float getRating() {
        float ratingF = (float) ((float) Math.round(rating * 2) / 2.0);
        return ratingF;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getPopularity() {
        return popularity;
    }

}
