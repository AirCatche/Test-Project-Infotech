package com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.city_weather

import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.Coordinates
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDto(
    @Json(name = "coord") val coords: Coordinates,
    @Json(name = "weather") val weather: List<Weather>,
    @Json(name = "main") val mainInfo: MainWeatherInfo,
    @Json(name = "wind") val wind: Wind,
)
