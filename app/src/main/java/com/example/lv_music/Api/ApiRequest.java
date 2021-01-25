package com.example.lv_music.Api;

import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Category;
import com.example.lv_music.Model.Singer;
import com.example.lv_music.Model.Song;
import com.example.lv_music.Model.SongItem;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
//    @GET("Server/all_songs.php")
//    Maybe<ApiResponse<List<Song>>> getAllSongs();
    // song
    @GET("apiLV_Music/all_songs.php")
    Maybe<ApiResponse<List<Song>>> getAllSongs();
    
    @FormUrlEncoded // parse sang dạng form để gửi lên
    @POST("apiLV_Music/all_songs_category.php")
    Maybe<ApiResponse<List<Song>>> getAllSongsCategory(
            @Field("cate_id") Integer cate_id
    );

    @FormUrlEncoded // parse sang dạng form để gửi lên
    @POST("apiLV_Music/song.php")
    Maybe<ApiResponse<Song>> getSong(
            @Field("id") Integer id
    );

    // advertisement
    @GET("apiLV_Music/all_advertisements.php")
    Maybe<ApiResponse<List<Advertisement>>> getAllAdvertisements();

    // category
    @GET("apiLV_Music/all_categories.php")
    Maybe<ApiResponse<List<Category>>> getAllCategories();
    @FormUrlEncoded // parse sang dạng form để gửi lên
    @POST("apiLV_Music/all_categories_song.php")
    Maybe<ApiResponse<List<Category>>> getAllCategoriesOfSong(
            @Field("song_id") Integer song_id
    );

    // song item
    @GET("apiLV_Music/all_song_items.php")
    Maybe<ApiResponse<List<SongItem>>> getAllSongItems();
    @FormUrlEncoded // parse sang dạng form để gửi lên
    @POST("apiLV_Music/all_song_items_category.php")
    Maybe<ApiResponse<List<SongItem>>> getAllSongItemsCategory(
            @Field("cate_id") Integer cate_id
    );

    // singer
    @GET("apiLV_Music/all_singers.php")
    Maybe<ApiResponse<List<Singer>>> getAllSingers();

    @FormUrlEncoded // parse sang dạng form để gửi lên
    @POST("apiLV_Music/all_singers_song.php")
    Maybe<ApiResponse<List<Singer>>> getAllSingersOfSong(
            @Field("song_id") Integer song_id
    );
}
