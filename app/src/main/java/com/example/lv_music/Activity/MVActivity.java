package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lv_music.Adapter.MVAdapter;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;
import com.example.lv_music.databinding.ActivityMVBinding;

public class MVActivity extends AppCompatActivity {
    ActivityMVBinding mBinding;
    LvMusicViewModel mViewModel;
    MVAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMVBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.tbMV);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.tbMV.setNavigationOnClickListener(view ->
                finish()
        );

        mViewModel = new ViewModelProvider(this).get(LvMusicViewModel.class);
        mViewModel.fetchAllSongItems();
        mViewModel.getResponseAllSongItems().observe(this, listApiResponse -> {
            mAdapter = new MVAdapter(this, listApiResponse.getData());
            mAdapter.setOnMVClickedListener(mv_link -> {
                try {
                    if(mv_link != null){
                        Intent intent = new Intent(this,YoutubeActivity.class);
                        intent.putExtra("mv_link",mv_link);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this, "Bài hát hiện chưa có link MV", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Log.d("EEE", e.getMessage());
                }
            });
            mBinding.rvMV.setAdapter(mAdapter);
        });
        mBinding.rvMV.setHasFixedSize(true);




    }
}