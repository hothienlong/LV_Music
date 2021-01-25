package com.example.lv_music.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lv_music.Activity.ListSingerActivity;
import com.example.lv_music.R;

public class LibraryFragment extends Fragment {

    View view;
    RelativeLayout libLayoutUpload, libLayoutMv, libLayoutSinger;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_library, container, false);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
        libLayoutSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListSingerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        libLayoutUpload = view.findViewById(R.id.libLayoutUpload);
        libLayoutMv = view.findViewById(R.id.libLayoutMv);
        libLayoutSinger = view.findViewById(R.id.libLayoutSinger);
    }
}
