package com.example.lv_music.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Song;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;

import java.util.List;

public class SongFragment extends Fragment {

    LvMusicViewModel mLvMusicViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, null);
        addControls();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        // 1: Fetch all song
        mLvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);

        mLvMusicViewModel.getResponseAllSongs().observe(getViewLifecycleOwner(), new Observer<ApiResponse<List<Song>>>() {
            @Override
            public void onChanged(ApiResponse<List<Song>> listApiResponse) {
                for(int i=0;i<listApiResponse.getData().size();i++){
                    Log.d("BBB", listApiResponse.getData().get(i).toString());
                }
            }
        });

        mLvMusicViewModel.fetchAllSongs();
    }

    private void addControls() {
    }
}
