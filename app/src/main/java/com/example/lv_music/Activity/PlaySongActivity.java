package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;


import com.example.lv_music.Adapter.PlaySongViewPager2Adapter;
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
import java.util.Random;
import java.util.logging.Logger;
import me.relex.circleindicator.CircleIndicator3;

public class PlaySongActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    PlaySongViewPager2Adapter playSongViewPagerAdapter;
    Toolbar toolbar;
    ImageView imgLikeSong, imgAddPlaylist, imgSuffle, imgBackward, imgPlaySong, imgForward, imgRepeat;
    CircleIndicator3 circleIndicator;
    TextThumbSeekBar textThumbSeekBar;

    LvMusicViewModel lvMusicViewModel;
    MediaPlayer mediaPlayer; //tất cả activity chỉ có 1 mediaplayer duy nhất
    ArrayList<SongItem> playSongItems = new ArrayList<>();
    Integer position = -1;
    Handler handlerPlayMusic = new Handler();;
    Runnable runnablePlayMusic;
    Boolean isMediaPrepared = false;

    Boolean repeated = false;
    Boolean suffled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        addControls();

    }

    @Override
    protected void onStart() {
        super.onStart();
        catchIntent();
        addEvents();
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

        // sự kiện seekbar
        textThumbSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            // kéo thumb
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handlerPlayMusic.removeCallbacks(runnablePlayMusic);
            }

            // dừng kéo thumb
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handlerPlayMusic.postDelayed(runnablePlayMusic, 100);
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playSongItems.size() > 0){
                    clearMediaPlayer();

                    blockUI();
                    if(repeated == false && suffled == false){
                        position++;
                    }
                    else if(suffled == true){
                        Random random = new Random();
                        Integer index = random.nextInt(playSongItems.size()-1);
                        if(index == position){
                            position++;
                        }
                        else {
                            position = index;
                        }
                    }
                    if(position == playSongItems.size()){
                        position = 0;
                    }
                    replaceLayoutFragment(playSongItems.get(position), playSongItems);
                    initLayoutActivity(playSongItems.get(position));
                    initMediaPlayer(playSongItems.get(position).getSong_link());

                    // Ngừng 4s mới đc bấm tiếp (ngăn tạo ra nhiều thread)
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unBlockUI();
                        }
                    }, 4000);
                }
            }
        });

        imgBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playSongItems.size() > 0){
                    clearMediaPlayer();

                    blockUI();
                    if(repeated == false && suffled == false){
                        position--;
                    }
                    else if(suffled == true){
                        Random random = new Random();
                        Integer index = random.nextInt(playSongItems.size()-1);
                        if(index == position){
                            position--;
                        }
                        else {
                            position = index;
                        }
                    }
                    if(position == -1){
                        position = playSongItems.size() - 1;
                    }
                    replaceLayoutFragment(playSongItems.get(position), playSongItems);
                    initLayoutActivity(playSongItems.get(position));
                    initMediaPlayer(playSongItems.get(position).getSong_link());

                    // Ngừng 4s mới đc bấm tiếp (ngăn tạo ra nhiều thread)
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unBlockUI();
                        }
                    }, 4000);
                }
            }
        });

        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatClick();
            }
        });

        imgSuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomClick();
            }
        });
    }

    private void repeatClick() {
        if(repeated == false){
            repeated = true;
            imgRepeat.setImageResource(R.drawable.ic_repeated);
            if(suffled == true){
                suffled = false;
                imgSuffle.setImageResource(R.drawable.ic_shuffle);
            }
        }
        else{
            repeated = false;
            imgRepeat.setImageResource(R.drawable.ic_repeat);
        }
    }

    private void randomClick() {
        if(suffled == false){
            suffled = true;
            imgSuffle.setImageResource(R.drawable.ic_shuffled);
            if(repeated == true){
                repeated = false;
                imgRepeat.setImageResource(R.drawable.ic_repeat);
            }
        }
        else{
            suffled = false;
            imgSuffle.setImageResource(R.drawable.ic_shuffle);
        }
    }

    private void upDateTimeSong() {
        runnablePlayMusic = new Runnable() {
            @Override
            public void run() {
                Log.d("CCC", "gau gau");
                if(mediaPlayer != null){
                    Log.d("CCC", "gay gay");

                    if(mediaPlayer.isPlaying()){
                        textThumbSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                    handlerPlayMusic.postDelayed(this, 100);
                }
            }
        };
        handlerPlayMusic.postDelayed(runnablePlayMusic, 100);
    }

    private void blockUI(){
        imgForward.setEnabled(false);
        imgBackward.setEnabled(false);
        textThumbSeekBar.setEnabled(false);
        imgPlaySong.setEnabled(false);
    }

    private void unBlockUI(){
        imgForward.setEnabled(true);
        imgBackward.setEnabled(true);
        textThumbSeekBar.setEnabled(true);
        imgPlaySong.setEnabled(true);
    }


    private void initMediaPlayer(String url) {
        Thread threadInitPlayer = new Thread(new Runnable() {
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
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                isMediaPrepared = true;
                                unBlockUI();
                                seekBarTime();
                                playMusic(); //có prepare nên lâu => lag giao diện
                            }
                        });
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                clearMediaPlayer();
                                finish();
                            }
                        });
                        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                clearMediaPlayer();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(PlaySongActivity.this, "Bạn thực hiện quá nhiều tác vụ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return true;
                            }
                        });
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadInitPlayer.start();

    }

    private void seekBarTime() {
        // get duration return int (milisecond) => format m:ss
        textThumbSeekBar.setMax(mediaPlayer.getDuration());
    }

    // clear when stop music
    private void clearMediaPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            handlerPlayMusic.removeCallbacks(runnablePlayMusic);

//            Toast.makeText(this, "clear", Toast.LENGTH_SHORT).show();
        }
    }

    private void playMusic(){
        if(mediaPlayer != null && isMediaPrepared == true){
            mediaPlayer.start();
            imgPlaySong.setImageResource(R.drawable.ic_pause_button);
            // cập nhật time song
            upDateTimeSong();

//        PlaySongFragment2.discStart();
        }
    }

    private void pauseMusic(){
        if(mediaPlayer != null && isMediaPrepared == true){
            mediaPlayer.pause();
            handlerPlayMusic.postDelayed(runnablePlayMusic, 100);
            imgPlaySong.setImageResource(R.drawable.ic_play_button);
//        PlaySongFragment2.discPause();
        }
    }

    // giao diện, thông tin, danh sách bài hát
    private void initLayoutFragment(SongItem songItem, ArrayList<SongItem> songItems) {
        playSongViewPagerAdapter = new PlaySongViewPager2Adapter(this,songItem,songItems);
        playSongViewPagerAdapter.addFragment(new PlaySongFragment1(songItem, songItems));
        playSongViewPagerAdapter.addFragment(new PlaySongFragment2());
        playSongViewPagerAdapter.addFragment(new PlaySongFragment3(songItem));
        viewPager.setAdapter(playSongViewPagerAdapter);
        // set circle indicator
        circleIndicator.setViewPager(viewPager);
    }

    private void replaceLayoutFragment(SongItem songItem, ArrayList<SongItem> songItems) {
        playSongViewPagerAdapter.replaceFragment(0, new PlaySongFragment1(songItem, songItems));
        playSongViewPagerAdapter.replaceFragment(1, new PlaySongFragment2());
        playSongViewPagerAdapter.replaceFragment(2, new PlaySongFragment3(songItem));
        viewPager.setAdapter(playSongViewPagerAdapter);
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

        blockUI();
    }

    private void catchIntent() {

        Intent intent = getIntent();

        if(intent != null){

            Bundle bundle = intent.getExtras();
            SongItem songItem = null;

            // Lấy danh sách bài hát
            if(intent.hasExtra("listsongitem")){
                // Nếu từ song page: tất cả các bài hát
                // Nếu từ songs category: tất cả các bài hát trong category
                // Nếu từ advertisement: ko có bài nào

                playSongItems = bundle.getParcelableArrayList("listsongitem");
            }

            // Lấy thông tin bài hát
            if(intent.hasExtra("position")){
                position = bundle.getInt("position");
                songItem = playSongItems.get(position);

                // phát ngẫu nhiên
                if(intent.hasExtra("isRandom")){
                    randomClick();
                }

//                Log.d("ABC",songItem.toString());
                // init layout before play music
                initLayoutFragment(songItem, playSongItems);

                initLayoutActivity(songItem);

                initMediaPlayer(songItem.getSong_link());

            }
            else if(intent.hasExtra("advertisement")) {

                Advertisement advertisement = bundle.getParcelable("advertisement");
                String songId = advertisement.getSongId();
                lvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);
                lvMusicViewModel.getResponseSongItem().observe(this, new Observer<ApiResponse<SongItem>>() {
                    @Override
                    public void onChanged(ApiResponse<SongItem> songItemApiResponse) {
                        // phát vòng lặp
                        repeatClick();

                        // init layout before play music
                        initLayoutFragment(songItemApiResponse.getData(), playSongItems);

                        initLayoutActivity(songItemApiResponse.getData());

                        initMediaPlayer(songItemApiResponse.getData().getSong_link());
                    }
                });
                lvMusicViewModel.fetchSongItem(Integer.parseInt(songId));
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearMediaPlayer();
    }
}