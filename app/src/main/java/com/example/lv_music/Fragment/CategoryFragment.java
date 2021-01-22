package com.example.lv_music.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv_music.Adapter.CategoryAdapter;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Category;
import com.example.lv_music.R;
import com.example.lv_music.ViewModel.LvMusicViewModel;

import java.util.List;

public class CategoryFragment extends Fragment {
    View view;
    LvMusicViewModel mLvMusicViewModel;
    RecyclerView mRcvCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        addControls();
        getData();
        return view;
    }

    private void getData() {
        mLvMusicViewModel = ViewModelProviders.of(this).get(LvMusicViewModel.class);

        // Fetch all categories
        mLvMusicViewModel.getResponseAllCategories().observe(getViewLifecycleOwner(), new Observer<ApiResponse<List<Category>>>() {
            @Override
            public void onChanged(ApiResponse<List<Category>> listApiResponse) {
//                for(int i=0;i<listApiResponse.getData().size();i++){
//                    Log.d("BBB", listApiResponse.getData().get(i).toString());
//                }
                CategoryAdapter categoryAdapter = new CategoryAdapter(listApiResponse.getData());

                mRcvCategory.setHasFixedSize(true);//tăng performance
                mRcvCategory.setAdapter(categoryAdapter);

            }
        });

        mLvMusicViewModel.fetchAllCategories();
    }

    private void addControls() {
        mRcvCategory = view.findViewById(R.id.categoryRecyclerview);
    }
}
