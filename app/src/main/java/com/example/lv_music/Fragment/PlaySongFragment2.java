package com.example.lv_music.Fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lv_music.R;

public class PlaySongFragment2 extends Fragment {

    ImageView imgDisc;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_song_2, null);
        addControls();

        return view;
    }

    private void addControls() {
        imgDisc = view.findViewById(R.id.imgDisc);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgDisc, "rotation", 0f, 360f);
        animator.setDuration(10000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        // set all the animation-related stuff you want (interpolator etc.)
        animator.start();
    }
}
