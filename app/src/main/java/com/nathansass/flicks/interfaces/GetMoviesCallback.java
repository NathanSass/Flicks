package com.nathansass.flicks.interfaces;

import org.json.JSONArray;

/**
 * Created by nathansass on 7/20/16.
 */
public interface GetMoviesCallback {
    void done(JSONArray movieJsonResults);
}
