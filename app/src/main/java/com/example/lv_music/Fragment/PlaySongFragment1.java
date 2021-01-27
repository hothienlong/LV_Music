package com.example.lv_music.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv_music.Adapter.ListSongAdapter;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Category;
import com.example.lv_music.Model.Singer;
import com.example.lv_music.Model.Song;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlaySongFragment1 extends Fragment {

    View view;
//    Fragment fragment;
    TextView tvSongName, tvSingerName, tvCategoryName, tvNumLike;
    RecyclerView songItemRecyclerview;
    ArrayList<SongItem> songItems;

    String mSongId;
    LvMusicViewModel mLvMusicViewModel;

    public PlaySongFragment1(String mSongId, ArrayList<SongItem> songItems) {
        this.mSongId = mSongId;
        this.songItems = songItems;
    }

    public PlaySongFragment1() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_song_1, null);
        addControls();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    // dùng map parse từ List<Object> -> List<Atribute of Obj>
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {

        // -- Gắn giao diện recyclerview
        ListSongAdapter listSongAdapter = new ListSongAdapter(songItems);

        //tăng performance
        songItemRecyclerview.setHasFixedSize(true);

        songItemRecyclerview.setAdapter(listSongAdapter);

        //animation
        songItemRecyclerview.scheduleLayoutAnimation();


        // -- Gắn giao diện thông tin bài hát
        mLvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);
        // get name of song
        mLvMusicViewModel.getResponseSong().observe(getViewLifecycleOwner(), new Observer<ApiResponse<Song>>() {
            @Override
            public void onChanged(ApiResponse<Song> songApiResponse) {
                tvSongName.setText(songApiResponse.getData().getName());
            }
        });

        // get categories of song
        mLvMusicViewModel.getResponseAllCategoriesOfSong().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<ApiResponse<List<Category>>>() {
            @Override
            public void onChanged(ApiResponse<List<Category>> listApiResponse) {
                List<String> categoryNames = listApiResponse.getData().stream()
                        .map(Category::getName)
                        .collect(Collectors.toList());
                tvCategoryName.setText(categoryNames.toString().substring(1, categoryNames.toString().length()-1));
            }
        });

        // get singers of song
        mLvMusicViewModel.getResponseAllSingersOfSong().observe(getViewLifecycleOwner(), new Observer<ApiResponse<List<Singer>>>() {
            @Override
            public void onChanged(ApiResponse<List<Singer>> listApiResponse) {
                List<String> singerNames = listApiResponse.getData().stream()
                        .map(Singer::getStageName)
                        .collect(Collectors.toList());
                tvSingerName.setText(singerNames.toString().substring(1, singerNames.toString().length()-1));
            }
        });
        mLvMusicViewModel.fetchAllCategoriesOfSong(Integer.parseInt(mSongId));
        mLvMusicViewModel.fetchAllSingersOfSong(Integer.parseInt(mSongId));
        mLvMusicViewModel.fetchSong(Integer.parseInt(mSongId));
    }

    private void addControls() {
        tvSingerName = view.findViewById(R.id.tvSingerName);
        tvSongName = view.findViewById(R.id.tvSongName);
        tvCategoryName = view.findViewById(R.id.tvCategoryName);
        tvNumLike = view.findViewById(R.id.tvNumLike);
        songItemRecyclerview = view.findViewById(R.id.songItemRecyclerview);
    }
}
