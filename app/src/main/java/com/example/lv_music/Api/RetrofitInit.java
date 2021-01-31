package com.example.lv_music.Api;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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


    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true) //khởi tạo lại nếu dữ liệu bị hư
                    .protocols(Arrays.asList(Protocol.HTTP_1_1)) //định tuyến đường dẫn ngắn nhất (các ứng dụng thường 1.1)
                    ;

            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private OkHttpClient getOkHttpClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) //khởi tạo lại nếu dữ liệu bị hư
                .protocols(Arrays.asList(Protocol.HTTP_1_1)) //định tuyến đường dẫn ngắn nhất (các ứng dụng thường 1.1)
                .build();
        return okHttpClient;
    }

    private Retrofit init() {
        Gson gson = new GsonBuilder().setLenient().create();


//        OkHttpClient okHttpClient = getUnsafeOkHttpClient();
        OkHttpClient okHttpClient = getOkHttpClient();


        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://longmusic.000webhostapp.com/")
//                .baseUrl("https://10.0.2.2/")
//                .baseUrl("http://192.168.0.7:8080/")
//                .baseUrl("http://192.168.1.87:8080/")
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
