package com.zyt.shine.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by zyt on 2016/3/28.
 */
public class RetrofitManager {
    private static Retrofit retrofit;
    private static ApiService apiService;
    private static OkHttpClient mOkHttpClient;

    public static ApiService getApiService() {
        if (retrofit == null) {
            mOkHttpClient = new OkHttpClient();
            mOkHttpClient.newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    //.client(mOkHttpClient)
                    .baseUrl("http://wthrcdn.etouch.cn/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
