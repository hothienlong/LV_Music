package com.example.lv_music.Repository;

import android.location.Address;

import com.example.lv_music.Api.ApiRequest;
import com.example.lv_music.Api.RetrofitInit;
import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Song;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;

public class AdvertisementRepository {
    private static AdvertisementRepository mInstance = null;
    private ApiRequest mApiRequest = null;

    // khởi tạo ApiRequest
    private AdvertisementRepository(){
        mApiRequest = RetrofitInit.getInstance();
    }

    // khởi tạo mInstance
    public static AdvertisementRepository getInstance(){
        if(mInstance == null){
            mInstance = new AdvertisementRepository();
        }
        return mInstance;
    }

    public Maybe<ApiResponse<List<Advertisement>>> getAllAdvertisements(){
        return mApiRequest.getAllAdvertisements();
    }

}
