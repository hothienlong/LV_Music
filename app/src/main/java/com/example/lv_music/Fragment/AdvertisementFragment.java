package com.example.lv_music.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.lv_music.Activity.PlaySongActivity;
import com.example.lv_music.Adapter.AdvertisementAdapter;
import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

// call api đọc dữ liệu -> dùng adapter đổ lên view
public class AdvertisementFragment extends Fragment {
    View view;
    LvMusicViewModel mLvMusicViewModel;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    Observer<ApiResponse<List<Advertisement>>> mObserver;
    AdvertisementAdapter mAdvertisementAdapter;

    int currentItem = 0;
    Handler handler;
    Runnable runnable;
    List<Advertisement> mAdvertisements;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdvertisementAdapter = new AdvertisementAdapter(getActivity());
        viewPager.setAdapter(mAdvertisementAdapter);
        mObserver = listApiResponse -> {
            mAdvertisements = listApiResponse.getData();
            mAdvertisementAdapter.submitList(mAdvertisements);
            // set circle indicator
            circleIndicator.setViewPager(viewPager);

        };
        handler = new Handler(Looper.myLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = viewPager.getCurrentItem();
                currentItem++;
                if(currentItem >= mAdvertisements.size()){
                    currentItem = 0;
                }
                viewPager.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable, 4000);
            }
        };
        handler.postDelayed(runnable, 4000);
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_advertisement, container, false);
        addControls();
        return view;
    }

    private void addControls() {
        viewPager = view.findViewById(R.id.advertisementViewPager);
        circleIndicator = view.findViewById(R.id.advertisementIndicator);
    }

    private void getData() {
        mLvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);

        // 3 : Fetch all advertisements
        mLvMusicViewModel.getResponseAllAdvertisements().observe(getViewLifecycleOwner(), mObserver);

        mLvMusicViewModel.fetchAllAdvertisements();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLvMusicViewModel.getResponseAllAdvertisements().removeObserver(mObserver);
    }
}
