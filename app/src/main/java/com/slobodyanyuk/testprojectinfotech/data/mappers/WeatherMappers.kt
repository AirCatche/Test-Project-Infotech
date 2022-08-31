package com.slobodyanyuk.testprojectinfotech.data.mappers

import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.city_weather.WeatherDto
import com.slobodyanyuk.testprojectinfotech.domain.entity.city_detail.WeatherInfo

fun WeatherDto.toWeatherInfo() = WeatherInfo(
    latitude = this.coords.latitude,
    longitude = this.coords.longitude,
    description = this.weather.first().description,
    windSpeed = this.wind.speed.toInt(),
    windDegree = this.wind.deg,
    humidity = this.mainInfo.humidity.toInt(),
    currentTemp = this.mainInfo.temp.toInt(),
    maxTemp = this.mainInfo.tempMax.toInt(),
    minTemp = this.mainInfo.tempMin.toInt()
)
