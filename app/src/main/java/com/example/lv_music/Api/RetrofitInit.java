package com.example.lv_music.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// dùng retrofit để tạo ApiRequest
public class RetrofitInit {
    private static RetrofitInit mInstance = null;
    private static Retrofit mRetrofit = null;

    // private constructor gốc lại => tạo mRetrofit (hay những gì đó cần khởi tạo)
    private RetrofitInit(){
        mRetrofit = init();
    }

    private Retrofit init() {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) //khởi tạo lại nếu dữ liệu bị hư
                .protocols(Arrays.asList(Protocol.HTTP_1_1)) //định tuyến đường dẫn ngắn nhất (các ứng dụng thường 1.1)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://longmusic.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson)) //convert json -> java
                .addCallAdapterFactory((RxJava3CallAdapterFactory.create()))
                .client(okHttpClient)
                .build();

        return mRetrofit;
    }

    // public singleton => khởi tạo mInstance, trả về ApiRequest
    public static ApiRequest getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitInit();
        }
        return mRetrofit.create(ApiRequest.class);
    }
}
