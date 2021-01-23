package com.example.lv_music.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Category;
import com.example.lv_music.Model.Singer;
import com.example.lv_music.Model.Song;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.Repository.AdvertisementRepository;
import com.example.lv_music.Repository.CategoryRepository;
import com.example.lv_music.Repository.SingerRepository;
import com.example.lv_music.Repository.SongItemRepository;
import com.example.lv_music.Repository.SongRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LvMusicViewModel extends ViewModel {
    private SongRepository mSongRepository;
    private MutableLiveData<ApiResponse<List<Song>>> mAllSongs;
    private MutableLiveData<ApiResponse<Song>> mSong;
    private MutableLiveData<ApiResponse<List<Song>>> mAllSongsCategory;
    private AdvertisementRepository mAdvertisementRepository;
    private MutableLiveData<ApiResponse<List<Advertisement>>> mAllAdvertisements;
    private CategoryRepository mCategoryRepository;
    private MutableLiveData<ApiResponse<List<Category>>> mAllCategories;
    private MutableLiveData<ApiResponse<List<Category>>> mAllCategoriesOfSong;
    private SongItemRepository mSongItemRepository;
    private MutableLiveData<ApiResponse<List<SongItem>>> mAllSongItems;
    private SingerRepository mSingerRepository;
    private MutableLiveData<ApiResponse<List<Singer>>> mAllSingersOfSong;

    public LvMusicViewModel(){
        mSongRepository = SongRepository.getInstance();
        mAllSongs = new MutableLiveData<>();
        mSong = new MutableLiveData<>();

        mAdvertisementRepository = AdvertisementRepository.getInstance();
        mAllAdvertisements = new MutableLiveData<>();

        mCategoryRepository = CategoryRepository.getInstance();
        mAllCategories = new MutableLiveData<>();
        mAllCategoriesOfSong = new MutableLiveData<>();

        mSongItemRepository = SongItemRepository.getInstance();
        mAllSongItems = new MutableLiveData<>();

        mSingerRepository = SingerRepository.getInstance();
        mAllSingersOfSong = new MutableLiveData<>();
    }

    public void fetchAllSongs(){
        mSongRepository.getAllSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<ApiResponse<List<Song>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse<List<Song>> listApiResponse) {
                        mAllSongs.setValue(listApiResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("BBB", "Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ApiResponse<List<Song>>> getResponseAllSongs(){
        return mAllSongs;
    }

    public void fetchSong(Integer id){
        mSongRepository.getSong(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<ApiResponse<Song>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse<Song> songApiResponse) {
                        mSong.setValue(songApiResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("BBB", "Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ApiResponse<Song>> getResponseSong(){
        return mSong;
    }

    public void fetchAllAdvertisements(){
        mAdvertisementRepository.getAllAdvertisements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<ApiResponse<List<Advertisement>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse<List<Advertisement>> listApiResponse) {
                        mAllAdvertisements.setValue(listApiResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("BBB", "Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ApiResponse<List<Advertisement>>> getResponseAllAdvertisements(){
        return mAllAdvertisements;
    }

    public void fetchAllCategories(){
        mCategoryRepository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<ApiResponse<List<Category>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse<List<Category>> listApiResponse) {
                        mAllCategories.setValue(listApiResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("BBB", "Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ApiResponse<List<Category>>> getResponseAllCategories(){
        return mAllCategories;
    }

    public void fetchAllCategoriesOfSong(Integer song_id){
        mCategoryRepository.getAllCategoriesOfSong(song_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<ApiResponse<List<Category>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse<List<Category>> listApiResponse) {
                        mAllCategoriesOfSong.setValue(listApiResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("BBB", "Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ApiResponse<List<Category>>> getResponseAllCategoriesOfSong(){
        return mAllCategoriesOfSong;
    }

    public void fetchAllSongItems(){
        mSongItemRepository.getAllSongItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<ApiResponse<List<SongItem>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse<List<SongItem>> listApiResponse) {
                        mAllSongItems.setValue(listApiResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("BBB", "Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ApiResponse<List<SongItem>>> getResponseAllSongItems(){
        return mAllSongItems;
    }

    public void fetchAllSongsCategory(){
        mSongRepository.getAllSongsCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<ApiResponse<List<Song>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse<List<Song>> listApiResponse) {
                        mAllSongsCategory.setValue(listApiResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("BBB", "Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ApiResponse<List<Song>>> getResponseAllSongsCategory(){
        return mAllSongsCategory;
    }

    public void fetchAllSingersOfSong(Integer song_id){
        mSingerRepository.getAllSingersOfSong(song_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<ApiResponse<List<Singer>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse<List<Singer>> listApiResponse) {
                        mAllSingersOfSong.setValue(listApiResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("BBB", "Error : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ApiResponse<List<Singer>>> getResponseAllSingersOfSong(){
        return mAllSingersOfSong;
    }
}
