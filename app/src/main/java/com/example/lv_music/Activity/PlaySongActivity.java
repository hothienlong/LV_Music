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
import android.view.View;
import android.widget.ImageView;


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

import me.relex.circleindicator.CircleIndicator;

public class PlaySongActivity extends AppCompatActivity {

    ViewPager viewPager;
    Toolbar toolbar;
    ImageView imgLikeSong, imgAddPlaylist, imgSuffle, imgBackward, imgPlaySong, imgForward, imgRepeat;
    CircleIndicator circleIndicator;
    TextThumbSeekBar textThumbSeekBar;

    LvMusicViewModel lvMusicViewModel;
    public static MediaPlayer mediaPlayer; //tất cả activity chỉ có 1 mediaplayer duy nhất


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        addControls();
        catchIntent();
    }

    private void addEvents() {
        imgPlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlaySongActivity.mediaPlayer != null){
                    if(!PlaySongActivity.mediaPlayer.isPlaying()){
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
        addEvents();
    }

    private void initMediaPlayer(String url) {

        // mỗi lần chỉ đúng 1 bài nhạc đc khởi tạo
        if(PlaySongActivity.mediaPlayer == null){
            PlaySongActivity.mediaPlayer = new MediaPlayer();
            try {
                PlaySongActivity.mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                );
                PlaySongActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        clearMediaPlayer();
                    }
                });
                PlaySongActivity.mediaPlayer.setDataSource(url);
                PlaySongActivity.mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        seekBarTime();
    }

    private void seekBarTime() {
        // get duration return int (milisecond) => format m:ss
        textThumbSeekBar.setMax(PlaySongActivity.mediaPlayer.getDuration());
    }


    private void clearMediaPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playMusic(){
        PlaySongActivity.mediaPlayer.start();
        imgPlaySong.setImageResource(R.drawable.ic_pause_button);
    }

    private void pauseMusic(){
        PlaySongActivity.mediaPlayer.pause();
        imgPlaySong.setImageResource(R.drawable.ic_play_button);
    }

    // giao diện, thông tin, danh sách bài hát
    private void initLayoutFragment(String songId, ArrayList<SongItem> songItems) {
        PlaySongViewPagerAdapter playSongViewPagerAdapter = new PlaySongViewPagerAdapter(getSupportFragmentManager());
        playSongViewPagerAdapter.addFragment(new PlaySongFragment1(songId, songItems));
        playSongViewPagerAdapter.addFragment(new PlaySongFragment2());
        playSongViewPagerAdapter.addFragment(new PlaySongFragment3(songId));
        viewPager.setAdapter(playSongViewPagerAdapter);
        // set circle indicator
        circleIndicator.setViewPager(viewPager);
    }

    public void initLayoutActivity(Song song){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(song.getName());
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
            lvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);
            String songId = null;
            ArrayList<SongItem> songItems = new ArrayList<>();

            // Lấy danh sách bài hát
            if(intent.hasExtra("listsongitem")){
                // Nếu từ song page: tất cả các bài hát
                // Nếu từ songs category: tất cả các bài hát trong category
                // Nếu từ advertisement: ko có bài nào
                songItems = (ArrayList<SongItem>) intent.getSerializableExtra("listsongitem");
            }

            // Lấy thông tin bài hát
            if(intent.hasExtra("song")){
                SongItem songItem = (SongItem) intent.getSerializableExtra("song");
//              Toast.makeText(this, songItem.getName(), Toast.LENGTH_SHORT).show();
                songId = songItem.getId();
            }
            else if(intent.hasExtra("advertisement")){
                Advertisement advertisement = (Advertisement) intent.getSerializableExtra("advertisement");
//                Log.d("BBB", advertisement.getSongId());
//                Toast.makeText(this, advertisement.toString(), Toast.LENGTH_SHORT).show();
                songId = advertisement.getSongId();
            }

            // init layout before play music
            initLayoutFragment(songId, songItems);

            lvMusicViewModel.getResponseSong().observe(this, new Observer<ApiResponse<Song>>() {
                @Override
                public void onChanged(ApiResponse<Song> songApiResponse) {
                    initLayoutActivity(songApiResponse.getData());
                    clearMediaPlayer();
                    initMediaPlayer(songApiResponse.getData().getSong_link());
                    playMusic(); //có prepare nên lâu => lag giao diện
                }
            });

            lvMusicViewModel.fetchSong(Integer.parseInt(songId));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearMediaPlayer();
    }
}