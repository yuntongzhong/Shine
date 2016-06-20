package com.zyt.shine.network;

import com.zyt.shine.bean.Example;
import com.zyt.shine.bean.Weather;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zyt on 2016/4/12.
 */
public interface ApiService {
//
    @GET("weather_mini")
    Observable<Example> getWeather(@Query("city") String city);

    @GET("weather_mini")
    Observable<String> getWeather2(@Query("citykey") int cityKey);


}
