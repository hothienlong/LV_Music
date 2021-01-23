package com.example.lv_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv_music.R;

public class PlaySongFragment1 extends Fragment {
    
    Fragment fragment;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_song_1, null);
//        addControls();
        init();
        return view;
    }

    private void init() {

    }

    private void addControls() {
        FragmentManager fragmentManager = getFragmentManager();
        fragment = fragmentManager.findFragmentByTag("playsong1");
        View view = fragment.getView();
        RecyclerView recyclerView = view.findViewById(R.id.songItemRecyclerview);
//        recyclerView.getitem
    }
}
