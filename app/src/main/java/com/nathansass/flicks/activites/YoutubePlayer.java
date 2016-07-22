package com.nathansass.flicks.activites;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nathansass.flicks.R;
import com.nathansass.flicks.models.Movie;
import com.nathansass.flicks.models.Movies;

public class YoutubePlayer extends YouTubeBaseActivity {

    Context context;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_youtube_player);

        context = this;

        int position = getIntent().getIntExtra("position", -1);
        movie = Movies.get().getMovies().get(position);

        String apiKey = "AIzaSyBC2fTR2r89OrwIeJqiXes1pY2KaZOldU4";

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize( apiKey,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.loadVideo(movie.getTrailerVideoKey());
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult errorReason) {
                        String error = String.format("Error initializing YouTube player: ", errorReason.toString());
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
}
