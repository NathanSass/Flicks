package com.nathansass.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nathansass on 7/18/16.
 */
public class Movie {
    String posterPath;

    String backdropPath;

    String originalTitle;

    String overview;

    Double rating;

    int popularity;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = jsonObject.getDouble("vote_average");
        this.popularity = jsonObject.getInt("popularity");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add( new Movie(array.getJSONObject(x)) );
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return results;
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

    public int getPopularity() {
        return popularity;
    }

}
