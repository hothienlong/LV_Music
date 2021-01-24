package com.example.lv_music.Repository;

import com.example.lv_music.Api.ApiRequest;
import com.example.lv_music.Api.RetrofitInit;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Song;
import com.example.lv_music.Model.SongItem;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;

public class SongItemRepository {
    private static SongItemRepository mInstance = null;
    private ApiRequest mApiRequest = null;

    // khởi tạo ApiRequest
    private SongItemRepository(){
        mApiRequest = RetrofitInit.getInstance();
    }

    // khởi tạo mInstance
    public static SongItemRepository getInstance(){
        if(mInstance == null){
            mInstance = new SongItemRepository();
        }
        return mInstance;
    }

    public Maybe<ApiResponse<List<SongItem>>> getAllSongItems(){
        return mApiRequest.getAllSongItems();
    }

    public Maybe<ApiResponse<List<SongItem>>> getAllSongItemsCategory(Integer cate_id){
        return mApiRequest.getAllSongItemsCategory(cate_id);
    }

}
