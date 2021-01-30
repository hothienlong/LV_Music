package com.example.lv_music.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.lv_music.Adapter.ListSingerAdapter;
import com.example.lv_music.Adapter.ListSongAdapter;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Singer;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;

import java.util.List;

public class ListSingerActivity extends AppCompatActivity {

    RecyclerView recyclerViewSinger;
    LvMusicViewModel lvMusicViewModel;
    Toolbar toolbar;
    SwipeRefreshLayout mSwiper;
    ListSingerAdapter mListSingerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_singer);
        addControls();
        getData();
        mSwiper.setOnRefreshListener(() -> {
            mListSingerAdapter.notifyDataSetChanged();
            mSwiper.setRefreshing(false);
        });
    }

    private void getData() {
        lvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);

        lvMusicViewModel.getResponseAllSingers().observe(this, new Observer<ApiResponse<List<Singer>>>() {
            @Override
            public void onChanged(ApiResponse<List<Singer>> listApiResponse) {
//                for(int i=0;i<listApiResponse.getData().size();i++){
//                    Log.d("BBB", listApiResponse.getData().get(i).toString());
//                }

                List<Singer> singers = listApiResponse.getData();

                setSupportActionBar(toolbar);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Nghệ sĩ (" + singers.size() + ")");
                actionBar.setDisplayHomeAsUpEnabled(true);

                setRecyclerView(singers);
            }
        });

        lvMusicViewModel.fetchAllSingers();
    }

    private void setRecyclerView(List<Singer> singers) {
//                for(int i=0;i<singers.size();i++){
//                    Log.d("BBB", singers.get(i).toString());
//                }
        mListSingerAdapter = new ListSingerAdapter(singers);

        //tăng performance
        recyclerViewSinger.setHasFixedSize(true);

        recyclerViewSinger.setAdapter(mListSingerAdapter);

        //animation
        recyclerViewSinger.scheduleLayoutAnimation();
    }

    private void addControls() {
        recyclerViewSinger = findViewById(R.id.singerRecyclerview);
        toolbar = findViewById(R.id.listSingersToolbar);
        mSwiper = findViewById(R.id.spRefresh);
    }
}