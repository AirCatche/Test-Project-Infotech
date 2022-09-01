package com.slobodyanyuk.testprojectinfotech.presentation.cities

sealed class CitiesEvent {
    data class InitScreenData(val jsonString: String) : CitiesEvent()
    data class SearchRequestChanged(val cityRequest: String) : CitiesEvent()
}
