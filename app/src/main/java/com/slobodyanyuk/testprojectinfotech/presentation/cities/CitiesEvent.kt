package com.slobodyanyuk.testprojectinfotech.presentation.cities

sealed class CitiesEvent {
    data class InitDataLoading(val jsonString: String) : CitiesEvent()
    data class SearchRequestChanged(val cityRequest: String) : CitiesEvent()
    data class OnCityClicked(val cityId: Int) : CitiesEvent()
}
