package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.Category;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;

public class SongCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_category);

        catchIntent();
    }

    private void catchIntent() {
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("category")){
                Category category = (Category) intent.getSerializableExtra("category");
                Toast.makeText(this, category.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}