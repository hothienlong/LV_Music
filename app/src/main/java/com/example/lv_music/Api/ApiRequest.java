package com.example.lv_music.Api;

import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Category;
import com.example.lv_music.Model.Song;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
//    @GET("Server/all_songs.php")
//    Maybe<ApiResponse<List<Song>>> getAllSongs();
    @GET("apiLV_Music/all_songs.php")
    Maybe<ApiResponse<List<Song>>> getAllSongs();

    @FormUrlEncoded // parse sang dạng form để gửi lên
    @POST("apiLV_Music/song.php")
    Maybe<ApiResponse<Song>> getSong(
            @Field("id") Integer id
    );

    @GET("apiLV_Music/all_advertisements.php")
    Maybe<ApiResponse<List<Advertisement>>> getAllAdvertisements();

    @GET("apiLV_Music/all_categories.php")
    Maybe<ApiResponse<List<Category>>> getAllCategories();
}
