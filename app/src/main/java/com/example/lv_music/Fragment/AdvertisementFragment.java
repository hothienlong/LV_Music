package com.example.lv_music.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.lv_music.Adapter.AdvertisementAdapter;
import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class AdvertisementFragment extends Fragment {
    View view;
    LvMusicViewModel mLvMusicViewModel;
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    int currentItem = 0;
    Handler handler;
    Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_advertisement, container, false);
        addControls();
        getData();
        return view;
    }

    private void addControls() {
        viewPager = view.findViewById(R.id.advertisementViewPager);
        circleIndicator = view.findViewById(R.id.advertisementIndicator);
    }

    private void getData() {
        mLvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);

        // 3 : Fetch all advertisements
        mLvMusicViewModel.getResponseAllAdvertisements().observe(getViewLifecycleOwner(), new Observer<ApiResponse<List<Advertisement>>>() {
            @Override
            public void onChanged(ApiResponse<List<Advertisement>> listApiResponse) {
//                for(int i=0;i<listApiResponse.getData().size();i++){
//                    Log.d("BBB", listApiResponse.getData().get(i).toString());
//                }
                AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(getActivity(), listApiResponse.getData());

                viewPager.setAdapter(advertisementAdapter);

                circleIndicator.setViewPager(viewPager);

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;
                        if(currentItem >= listApiResponse.getData().size()){
                            currentItem = 0;
                        }
                        viewPager.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable, 4000);
                    }
                };
                handler.postDelayed(runnable, 4000);
            }
        });

        mLvMusicViewModel.fetchAllAdvertisements();
    }
}
