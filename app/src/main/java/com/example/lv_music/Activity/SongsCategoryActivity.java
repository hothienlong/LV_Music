package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.squareup.picasso.Target;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

// tương tự như list song fragment & fragment_list_song
public class SongsCategoryActivity extends AppCompatActivity {

    Category mCategory;
    LvMusicViewModel mLvMusicViewModel;
    RecyclerView songItemRecyclerview;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imgCategory;
    FloatingActionButton floatBtnRandom;
    Toolbar toolbar;
    SwipeRefreshLayout mSwiper;
    ListSongAdapter mListSongAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_category);

        addControls();
        catchIntent();
        init();
        getData();

    }

    private void init() {
        collapsingToolbarLayout.setTitle(mCategory.getName());
        Picasso.get().load(mCategory.getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                collapsingToolbarLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
                imgCategory.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d("BBB", "Load bit map false");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d("BBB", "Prepare load bit map false");
            }
        });

        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);

        mSwiper.setOnRefreshListener(() -> {
            mListSongAdapter.notifyDataSetChanged();
            mSwiper.setRefreshing(false);
        });
    }

    private void addControls() {
        songItemRecyclerview = findViewById(R.id.songItemRecyclerview);
        collapsingToolbarLayout = findViewById(R.id.collapseBarSongsCategory);
        imgCategory = findViewById(R.id.imgCategory);
        floatBtnRandom = findViewById(R.id.floatBtnRandom);
        toolbar = findViewById(R.id.songsCategoryToolbar);
        mSwiper = findViewById(R.id.spRefresh);
    }

    private void getData() {
        mLvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);

        mLvMusicViewModel.getResponseAllSongItemsCategory().observe(this, new Observer<ApiResponse<List<SongItem>>>() {
            @Override
            public void onChanged(ApiResponse<List<SongItem>> listApiResponse) {
//                for(int i=0;i<listApiResponse.getData().size();i++){
//                    Log.d("BBB", listApiResponse.getData().get(i).toString());
//                }

                // dùng lại adapter của list song
                mListSongAdapter = new ListSongAdapter((ArrayList<SongItem>) listApiResponse.getData());

                //tăng performance
                songItemRecyclerview.setHasFixedSize(true);

                songItemRecyclerview.setAdapter(mListSongAdapter);

                //animation
                songItemRecyclerview.scheduleLayoutAnimation();

                floatBtnRandom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SongsCategoryActivity.this, PlaySongActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("listsongitem", (ArrayList<? extends Parcelable>) listApiResponse.getData());
                        Integer position = new Random().nextInt(listApiResponse.getData().size()-1);
                        bundle.putInt("position", position);
                        bundle.putBoolean("isRandom", true);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });

        mLvMusicViewModel.fetchAllSongItemsCategory(Integer.parseInt(mCategory.getId()));
    }

    private void catchIntent() {
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(intent.hasExtra("category")){
                mCategory = bundle.getParcelable("category");
//                Toast.makeText(this, mCategory.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}