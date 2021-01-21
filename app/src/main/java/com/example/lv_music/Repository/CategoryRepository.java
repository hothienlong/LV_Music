package com.example.lv_music.Repository;

import com.example.lv_music.Api.ApiRequest;
import com.example.lv_music.Api.RetrofitInit;
import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Category;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;

public class CategoryRepository {
    private static CategoryRepository mInstance = null;
    private ApiRequest mApiRequest = null;

    // khởi tạo ApiRequest
    private CategoryRepository(){
        mApiRequest = RetrofitInit.getInstance();
    }

    // khởi tạo mInstance
    public static CategoryRepository getInstance(){
        if(mInstance == null){
            mInstance = new CategoryRepository();
        }
        return mInstance;
    }

    public Maybe<ApiResponse<List<Category>>> getAllCategories(){
        return mApiRequest.getAllCategories();
    }

}
