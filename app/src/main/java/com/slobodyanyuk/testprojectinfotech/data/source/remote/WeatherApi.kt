package com.slobodyanyuk.testprojectinfotech.data.source.remote

import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.city_weather.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather/?units=metric")
    suspend fun getWeatherById(
        @Query("id") cityId: Int,
        @Query("appid") key: String,
    ): WeatherDto
}