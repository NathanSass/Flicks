package com.nathansass.flicks.models;

/**
 * Created by nathansass on 7/20/16.
 */
public class Movies extends SuperMovies {
    private static Movies instance = null;

    protected Movies() {
        // Exists only to defeat instantiation.
    }

    public static Movies get() {
        if (instance == null) {
            instance = new Movies();
        }

        return instance;
    }
}
