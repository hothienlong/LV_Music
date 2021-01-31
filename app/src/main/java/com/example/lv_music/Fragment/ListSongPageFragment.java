package com.example.lv_music.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.lv_music.Activity.PlaySongActivity;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;

import java.util.ArrayList;
import java.util.Random;

public class ListSongPageFragment extends Fragment {

    View view;
    Button btnRandom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_song_page, container, false);
        addControls();
        return view;
    }

    public void RandomSong(ArrayList<SongItem> songItems) {
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaySongActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("listsongitem", songItems);
                Integer position = new Random().nextInt(songItems.size()-1);
                bundle.putInt("position", position);
                bundle.putBoolean("isRandom", true);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnRandom = view.findViewById(R.id.btnRandom);
    }

}
