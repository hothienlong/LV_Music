package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lv_music.Adapter.ListSongAdapter;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Category;
import com.example.lv_music.Model.Song;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.Logger;

// tương tự như list song fragment & fragment_list_song
public class SongsCategoryActivity extends AppCompatActivity {

    Category mCategory;
    LvMusicViewModel mLvMusicViewModel;
    RecyclerView songItemRecyclerview;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imgCategory;
    FloatingActionButton floatBtnRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_category);

        addControls();
        catchIntent();
        getData();

    }

    private void addControls() {
        songItemRecyclerview = findViewById(R.id.songItemRecyclerview);
        collapsingToolbarLayout = findViewById(R.id.collapseBarSongsCategory);
        imgCategory = findViewById(R.id.imgCategory);
        floatBtnRandom = findViewById(R.id.floatBtnRandom);
    }

    private void getData() {


        mLvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);

        mLvMusicViewModel.getResponseAllSongItemsCategory().observe(this, new Observer<ApiResponse<List<SongItem>>>() {
            @Override
            public void onChanged(ApiResponse<List<SongItem>> listApiResponse) {
//                for(int i=0;i<listApiResponse.getData().size();i++){
//                    Log.d("BBB", listApiResponse.getData().get(i).toString());
//                }

                ListSongAdapter listSongAdapter = new ListSongAdapter(listApiResponse.getData());

                //tăng performance
                songItemRecyclerview.setHasFixedSize(true);

                songItemRecyclerview.setAdapter(listSongAdapter);

                //animation
                songItemRecyclerview.scheduleLayoutAnimation();
            }
        });

        mLvMusicViewModel.fetchAllSongItemsCategory(Integer.parseInt(mCategory.getId()));
    }

    private void catchIntent() {
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("category")){
                mCategory = (Category) intent.getSerializableExtra("category");
                Toast.makeText(this, mCategory.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}