package com.slobodyanyuk.testprojectinfotech.domain.use_case.city_details

import com.slobodyanyuk.testprojectinfotech.domain.repository.WeatherRepository

class LoadWeatherData(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        cityId: Int,
        apiKey: String,
    ) = repository.getWeatherById(cityId, apiKey)
}