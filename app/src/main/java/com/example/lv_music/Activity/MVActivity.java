package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.lv_music.Adapter.MVAdapter;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;
import com.example.lv_music.databinding.ActivityMVBinding;

public class MVActivity extends AppCompatActivity {
    ActivityMVBinding mBinding;
    LvMusicViewModel mViewModel;

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
            MVAdapter adapter = new MVAdapter(this, listApiResponse.getData());
            mBinding.rvMV.setAdapter(adapter);
        });
        mBinding.rvMV.setHasFixedSize(true);


    }
}