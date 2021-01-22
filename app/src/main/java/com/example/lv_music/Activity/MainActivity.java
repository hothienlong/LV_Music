package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.lv_music.Adapter.MainViewPagerAdapter;
import com.example.lv_music.Fragment.AccountFragment;
import com.example.lv_music.Fragment.HomeFragment;
import com.example.lv_music.Fragment.PlaylistFragment;
import com.example.lv_music.Fragment.SongFragment;
import com.example.lv_music.Fragment.TopListFragment;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    LvMusicViewModel mLvMusicViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        init();

//        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);


//        // 2 : Fetch a song
//        mMainViewModel.getResponseSong().observe(this, new Observer<ApiResponse<Song>>() {
//            @Override
//            public void onChanged(ApiResponse<Song> songApiResponse) {
//                Log.d("BBB", songApiResponse.getData().toString());
//            }
//        });
//
//        mMainViewModel.fetchSong(3);

    }

    private void init() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new SongFragment(), "Song");
        adapter.addFragment(new PlaylistFragment(), "Play list");
        adapter.addFragment(new TopListFragment(), "Top list");
        adapter.addFragment(new AccountFragment(), "Information");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.song);
        tabLayout.getTabAt(2).setIcon(R.drawable.playlist);
        tabLayout.getTabAt(3).setIcon(R.drawable.top);
        tabLayout.getTabAt(4).setIcon(R.drawable.account);
    }

    private void addControls() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
    }
}