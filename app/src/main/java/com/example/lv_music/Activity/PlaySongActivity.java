package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Toast;

import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;

public class PlaySongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        catchIntent();
    }

    private void catchIntent() {
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("song")){
                SongItem songItem = (SongItem) intent.getSerializableExtra("song");
                Toast.makeText(this, songItem.getName(), Toast.LENGTH_SHORT).show();
            }
            else if(intent.hasExtra("advertisement")){
                Advertisement advertisement = (Advertisement) intent.getSerializableExtra("advertisement");
                Toast.makeText(this, advertisement.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}