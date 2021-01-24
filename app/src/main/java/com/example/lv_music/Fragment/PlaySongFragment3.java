package com.example.lv_music.Fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Song;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;

public class PlaySongFragment3 extends Fragment {

    TextView tvLyric;
    View view;

    String songId;
    LvMusicViewModel mLvMusicViewModel;

    public PlaySongFragment3(String songId) {
        this.songId = songId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_song_3, container, false);
        addControls();
        init();
        return view;
    }

    private void init() {
        mLvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);

        // get name of song
        mLvMusicViewModel.getResponseSong().observe(getViewLifecycleOwner(), new Observer<ApiResponse<Song>>() {
            @Override
            public void onChanged(ApiResponse<Song> songApiResponse) {
                tvLyric.setText(HtmlCompat.fromHtml(songApiResponse.getData().getLyric(), HtmlCompat.FROM_HTML_MODE_LEGACY));
//                tvLyric.setText(songApiResponse.getData().getLyric());
            }
        });

        mLvMusicViewModel.fetchSong(Integer.parseInt(songId));
    }

    private void addControls() {
        tvLyric = view.findViewById(R.id.tvLyric);
    }
}
