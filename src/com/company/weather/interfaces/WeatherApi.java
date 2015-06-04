package com.company.weather.interfaces;

import com.company.weather.model.WeatherInfo;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface WeatherApi {

	@GET("/api/v1/")
	public void getWeather(@Query("lat") Double latitude,@Query("lng") Double longitude,Callback<WeatherInfo> response);
}
