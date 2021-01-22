package com.example.lv_music.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.lv_music.Api.ApiRequest;
import com.example.lv_music.Api.RetrofitInit;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Song;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;

public class SongRepository {
    private static SongRepository mInstance = null;
    private ApiRequest mApiRequest = null;

    // khởi tạo ApiRequest
    private SongRepository(){
        mApiRequest = RetrofitInit.getInstance();
    }

    // khởi tạo mInstance
    public static SongRepository getInstance(){
        if(mInstance == null){
            mInstance = new SongRepository();
        }
        return mInstance;
    }

    public Maybe<ApiResponse<List<Song>>> getAllSongs(){
        return mApiRequest.getAllSongs();
    }

    public Maybe<ApiResponse<Song>> getSong(Integer id){
        return mApiRequest.getSong(id);
    }

    public Maybe<ApiResponse<List<Song>>> getAllSongsCategory(){
        return mApiRequest.getAllSongsCategory();
    }
}
