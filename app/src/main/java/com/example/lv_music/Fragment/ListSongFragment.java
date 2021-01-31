package com.example.lv_music.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lv_music.Activity.PlaySongActivity;
import com.example.lv_music.Adapter.ListSongAdapter;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ListSongFragment extends Fragment {

    View view;

    LvMusicViewModel mLvMusicViewModel;

    RecyclerView songItemRecyclerview;
    SwipeRefreshLayout mSwiper;
    ListSongAdapter mListSongAdapter;
    Button btnRandom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_song, null);
        addControls();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
        mSwiper.setOnRefreshListener(() -> {
            mListSongAdapter.notifyDataSetChanged();
            mSwiper.setRefreshing(false);
        });
    }

    private void getData() {
        // 1: Fetch all song
        mLvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);

        mLvMusicViewModel.getResponseAllSongItems().observe(getActivity(), new Observer<ApiResponse<List<SongItem>>>() {
            @Override
            public void onChanged(ApiResponse<List<SongItem>> listApiResponse) {
//                for(int i=0;i<listApiResponse.getData().size();i++){
//                    Log.d("BBB", listApiResponse.getData().get(i).toString());
//                }
//                Log.d("ABC",listApiResponse.getData().toString());

                mListSongAdapter = new ListSongAdapter((ArrayList<SongItem>) listApiResponse.getData());

                //tăng performance
                songItemRecyclerview.setHasFixedSize(true);

                songItemRecyclerview.setAdapter(mListSongAdapter);

                //animation
                songItemRecyclerview.scheduleLayoutAnimation();

                // Sự kiện nút phát ngẫu nhiên
                btnRandom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PlaySongActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("listsongitem", (ArrayList<? extends Parcelable>) listApiResponse.getData());
                        Integer position = new Random().nextInt(listApiResponse.getData().size()-1);
                        bundle.putInt("position", position);
                        bundle.putBoolean("isRandom", true);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            }
        });

        mLvMusicViewModel.fetchAllSongItems();

    }

    private void addControls() {
        songItemRecyclerview = view.findViewById(R.id.songItemRecyclerview);
        mSwiper = view.findViewById(R.id.spRefresh);
        btnRandom = view.findViewById(R.id.btnRandom);
    }
}
