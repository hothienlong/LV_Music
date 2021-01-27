package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.lv_music.Adapter.PlaySongViewPagerAdapter;
import com.example.lv_music.Fragment.PlaySongFragment1;
import com.example.lv_music.Fragment.PlaySongFragment2;
import com.example.lv_music.Fragment.PlaySongFragment3;
import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Song;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;
import com.example.lv_music.TextThumbSeekBar;
import com.example.lv_music.ViewModel.LvMusicViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

import me.relex.circleindicator.CircleIndicator;

public class PlaySongActivity extends AppCompatActivity {

    ViewPager viewPager;
    Toolbar toolbar;
    ImageView imgLikeSong, imgAddPlaylist, imgSuffle, imgBackward, imgPlaySong, imgForward, imgRepeat;
    CircleIndicator circleIndicator;
    TextThumbSeekBar textThumbSeekBar;

    LvMusicViewModel lvMusicViewModel;
    MediaPlayer mediaPlayer; //tất cả activity chỉ có 1 mediaplayer duy nhất
    ArrayList<SongItem> songItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        addControls();

    }

    private void addEvents() {
        imgPlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    if(!mediaPlayer.isPlaying()){
                        playMusic();
                    }
                    else {
                        pauseMusic();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        catchIntent();
        addEvents();
    }


    private void initMediaPlayer(String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                clearMediaPlayer();
                // mỗi lần chỉ đúng 1 bài nhạc đc khởi tạo
                if(mediaPlayer == null){
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setAudioAttributes(
                                new AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .build()
                        );
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                clearMediaPlayer();
                            }
                        });
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                playMusic(); //có prepare nên lâu => lag giao diện
                seekBarTime();
            }
        }).start();

    }

    private void seekBarTime() {
        // get duration return int (milisecond) => format m:ss
        textThumbSeekBar.setMax(mediaPlayer.getDuration());
    }


    private void clearMediaPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playMusic(){
        mediaPlayer.start();
        imgPlaySong.setImageResource(R.drawable.ic_pause_button);
    }

    private void pauseMusic(){
        mediaPlayer.pause();
        imgPlaySong.setImageResource(R.drawable.ic_play_button);
    }

    // giao diện, thông tin, danh sách bài hát
    private void initLayoutFragment(SongItem songItem, ArrayList<SongItem> songItems) {
        PlaySongViewPagerAdapter playSongViewPagerAdapter = new PlaySongViewPagerAdapter(getSupportFragmentManager());
        playSongViewPagerAdapter.addFragment(new PlaySongFragment1(songItem, songItems));
        playSongViewPagerAdapter.addFragment(new PlaySongFragment2());
        playSongViewPagerAdapter.addFragment(new PlaySongFragment3(songItem));
        viewPager.setAdapter(playSongViewPagerAdapter);
        // set circle indicator
        circleIndicator.setViewPager(viewPager);
    }

    public void initLayoutActivity(SongItem songItem){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(songItem.getName());
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
        textThumbSeekBar = findViewById(R.id.textSeekbarPlaySong);
    }

    private void catchIntent() {

        Intent intent = getIntent();

        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                SongItem songItem = null;


                // Lấy danh sách bài hát
                if(intent.hasExtra("listsongitem")){
                    // Nếu từ song page: tất cả các bài hát
                    // Nếu từ songs category: tất cả các bài hát trong category
                    // Nếu từ advertisement: ko có bài nào

                    songItems = bundle.getParcelableArrayList("listsongitem");
                }

                // Lấy thông tin bài hát
                if(intent.hasExtra("songitem")){
                    songItem = (SongItem) bundle.getParcelable("songitem");

                    // init layout before play music
                    initLayoutFragment(songItem, songItems);

                    initLayoutActivity(songItem);

                    initMediaPlayer(songItem.getSong_link());
                }
                else if(intent.hasExtra("advertisement")){
                    Advertisement advertisement = (Advertisement) intent.getSerializableExtra("advertisement");
                    String songId = advertisement.getSongId();
                    lvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);
                    lvMusicViewModel.getResponseSongItem().observe(this, new Observer<ApiResponse<SongItem>>() {
                        @Override
                        public void onChanged(ApiResponse<SongItem> songItemApiResponse) {
                            // init layout before play music
                            initLayoutFragment(songItemApiResponse.getData(), songItems);

                            initLayoutActivity(songItemApiResponse.getData());

                            initMediaPlayer(songItemApiResponse.getData().getSong_link());

                        }
                    });
                    lvMusicViewModel.fetchSongItem(Integer.parseInt(songId));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearMediaPlayer();
    }
}