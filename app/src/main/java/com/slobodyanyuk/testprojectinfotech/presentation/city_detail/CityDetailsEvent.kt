package com.slobodyanyuk.testprojectinfotech.presentation.city_detail

sealed class CityDetailsEvent {
    data class LoadWeatherInfo(val cityId: Int) : CityDetailsEvent()
}