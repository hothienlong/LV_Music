package com.example.lv_music.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lv_music.Model.ApiResponse;
import com.example.lv_music.Model.Song;
import com.example.lv_music.Repository.SongRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SongViewModel extends ViewModel {
    private SongRepository mSongRepository;
    private MutableLiveData<ApiResponse<List<Song>>> mSongs;

    public SongViewModel(){
        mSongs = new MutableLiveData<>();
        mSongRepository = SongRepository.getInstance();
    }

    public void fetchSongs(){
        mSongRepository.getSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<ApiResponse<List<Song>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse<List<Song>> listApiResponse) {
                        mSongs.setValue(listApiResponse);
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

    public LiveData<ApiResponse<List<Song>>> getResponseSongs(){
        return mSongs;
    }
}
