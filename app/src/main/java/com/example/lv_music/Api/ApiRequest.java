package com.example.lv_music.Api;

import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Song;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import retrofit2.http.GET;

public interface ApiRequest {
    @GET("Server/all_songs.php")
    Maybe<ApiResponse<List<Song>>> getSongs();
}
