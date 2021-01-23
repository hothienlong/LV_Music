package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.lv_music.Adapter.PlaySongViewPagerAdapter;
import com.example.lv_music.Fragment.PlaySongFragment1;
import com.example.lv_music.Fragment.PlaySongFragment2;
import com.example.lv_music.Fragment.PlaySongFragment3;
import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;

import me.relex.circleindicator.CircleIndicator;

public class PlaySongActivity extends AppCompatActivity {

    ViewPager viewPager;
    Toolbar toolbar;
    ImageView imgLikeSong, imgAddPlaylist, imgSuffle, imgBackward, imgPlaySong, imgForward, imgRepeat;
    CircleIndicator circleIndicator;

    String mSongId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        addControls();
        catchIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();

    }

    private void init() {
        PlaySongViewPagerAdapter playSongViewPagerAdapter = new PlaySongViewPagerAdapter(getSupportFragmentManager());
        playSongViewPagerAdapter.addFragment(new PlaySongFragment1(mSongId));
        playSongViewPagerAdapter.addFragment(new PlaySongFragment2());
        playSongViewPagerAdapter.addFragment(new PlaySongFragment3());
        viewPager.setAdapter(playSongViewPagerAdapter);
        // set circle indicator
        circleIndicator.setViewPager(viewPager);
    }

    private void addControls() {
        viewPager = findViewById(R.id.playSongViewPager);
        circleIndicator = findViewById(R.id.playSongIndicator);
        toolbar =  findViewById(R.id.playSongToolbar);
        imgLikeSong = findViewById(R.id.imgLikeSong);
        imgAddPlaylist = findViewById(R.id.imgAddPlaylist);
        imgSuffle = findViewById(R.id.imgSuffle);
        imgBackward = findViewById(R.id.imgBackward);
        imgPlaySong = findViewById(R.id.imgPlaySong);
        imgForward = findViewById(R.id.imgForward);
        imgRepeat = findViewById(R.id.imgRepeat);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void catchIntent() {
        Intent intent = getIntent();

        if(intent != null){
            if(intent.hasExtra("song")){
                SongItem songItem = (SongItem) intent.getSerializableExtra("song");
//              Toast.makeText(this, songItem.getName(), Toast.LENGTH_SHORT).show();
                mSongId = songItem.getId();
            }
            else if(intent.hasExtra("advertisement")){
                Advertisement advertisement = (Advertisement) intent.getSerializableExtra("advertisement");
//                Log.d("BBB", advertisement.getSongId());
//                Toast.makeText(this, advertisement.toString(), Toast.LENGTH_SHORT).show();
                mSongId = advertisement.getSongId();
            }
        }
    }
}