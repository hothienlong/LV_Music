package com.example.lv_music.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.lv_music.Fragment.PlaySongFragment1;
import com.example.lv_music.Fragment.PlaySongFragment2;
import com.example.lv_music.Fragment.PlaySongFragment3;
import com.example.lv_music.Model.SongItem;

import java.util.ArrayList;
import java.util.List;

public class PlaySongViewPager2Adapter extends FragmentStateAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();
//    Fragment mFragment = null;
//    SongItem songItem;
//    List<SongItem> songItems;
    public PlaySongViewPager2Adapter(@NonNull FragmentActivity fragmentActivity,SongItem songItem, List<SongItem> songItems) {
        super(fragmentActivity);
//        this.songItem = songItem;
//        this.songItems = songItems;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
//        switch (position) {
//            case 0:
//                mFragment = new PlaySongFragment1(songItem, (ArrayList<SongItem>) songItems);
//
//                return mFragment;
//            case 1:
//                mFragment = new PlaySongFragment2();
//
//                return mFragment;
//            case 2:
//                mFragment = new PlaySongFragment3(songItem);
//
//                return mFragment;
//        }
//        return new PlaySongFragment1(songItem, (ArrayList<SongItem>) songItems);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
//        return 3;
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    public void replaceFragment(Integer index, Fragment fragment){
        fragments.set(index, fragment);
    }
}
