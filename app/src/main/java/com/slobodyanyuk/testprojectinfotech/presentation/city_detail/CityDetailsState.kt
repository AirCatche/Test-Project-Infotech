package com.slobodyanyuk.testprojectinfotech.presentation.city_detail

import com.slobodyanyuk.testprojectinfotech.domain.entity.city_detail.WeatherInfo

data class CityDetailsState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)