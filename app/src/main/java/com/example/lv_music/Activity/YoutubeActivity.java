package com.example.lv_music.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lv_music.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity {
    public static final String API_KEY = "AIzaSyC5epPj6FmAJSCMDf7zDEHtpOthSSN-gxM";
    YouTubePlayerView mYouTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        Intent intent = getIntent();
        String videoLink= intent.getStringExtra("mv_link");
        String videoID = videoLink.substring(videoLink.length()-11);
        Log.d("ABC",videoID);
        mYouTubePlayerView = findViewById(R.id.youtubePlayerView);
        YouTubePlayer.OnInitializedListener initPlayer = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if(youTubeInitializationResult.isUserRecoverableError())
                    youTubeInitializationResult.getErrorDialog(YoutubeActivity.this,0);
            }
        };
        mYouTubePlayerView.initialize(API_KEY,initPlayer);
    }
}
