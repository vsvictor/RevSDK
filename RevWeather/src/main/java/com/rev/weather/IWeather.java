package com.rev.weather;

import com.rev.weather.model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by victor on 29.03.17.
 */

public interface IWeather {
    @GET("weather")
    Call<Root> getWeatherByCoordinate(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String key);
}
