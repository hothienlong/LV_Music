package com.example.lv_music.Repository;

import com.example.lv_music.Api.ApiRequest;
import com.example.lv_music.Api.RetrofitInit;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Category;
import com.example.lv_music.Model.Singer;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;

public class SingerRepository {
    private static SingerRepository mInstance = null;
    private ApiRequest mApiRequest = null;

    // khởi tạo ApiRequest
    private SingerRepository(){
        mApiRequest = RetrofitInit.getInstance();
    }

    // khởi tạo mInstance
    public static SingerRepository getInstance(){
        if(mInstance == null){
            mInstance = new SingerRepository();
        }
        return mInstance;
    }

    public Maybe<ApiResponse<List<Singer>>> getAllSingersOfSong(Integer song_id){
        return mApiRequest.getAllSingersOfSong(song_id);
    }
}
