package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.accounts.Account;
import android.os.Bundle;
import android.util.Log;

import com.example.lv_music.Adapter.MainViewPagerAdapter;
import com.example.lv_music.Fragment.AccountFragment;
import com.example.lv_music.Fragment.HomeFragment;
import com.example.lv_music.Fragment.PlaylistFragment;
import com.example.lv_music.Fragment.TopListFragment;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Song;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.SongViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    SongViewModel mSongViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        init();

        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);

        // 1: Fetch Song
        mSongViewModel.getResponseSongs().observe(this, new Observer<ApiResponse<List<Song>>>() {
            @Override
            public void onChanged(ApiResponse<List<Song>> listApiResponse) {
                Log.d("BBB", "change");
                for(int i=0;i<listApiResponse.getData().size();i++){
                    Log.d("BBB", listApiResponse.getData().get(i).toString());
                }
            }
        });

        mSongViewModel.fetchSongs();
    }

    private void init() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new PlaylistFragment(), "Playlist");
        adapter.addFragment(new TopListFragment(), "Toplist");
        adapter.addFragment(new AccountFragment(), "Information");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.playlist);
        tabLayout.getTabAt(2).setIcon(R.drawable.top);
        tabLayout.getTabAt(3).setIcon(R.drawable.account);
    }

    private void addControls() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
    }
}