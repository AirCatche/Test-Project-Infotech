package com.slobodyanyuk.testprojectinfotech.domain.entity.city_detail

data class WeatherInfo(
    val latitude: Float? = null,
    val longitude: Float? = null,
    val description: String? = null,
    val windSpeed: Int? = null,
    val windDegree: Int? = null,
    val humidity: Int? = null,
    val currentTemp: Int? = null,
    val minTemp: Int? = null,
    val maxTemp: Int? = null,
)