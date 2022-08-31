package com.slobodyanyuk.testprojectinfotech.domain.repository

import com.slobodyanyuk.testprojectinfotech.domain.entity.city_detail.WeatherInfo
import com.slobodyanyuk.testprojectinfotech.domain.util.Resource

interface WeatherRepository {

    suspend fun getWeatherById(
        cityId: Int,
        apiKey: String,
    ): Resource<WeatherInfo>
}