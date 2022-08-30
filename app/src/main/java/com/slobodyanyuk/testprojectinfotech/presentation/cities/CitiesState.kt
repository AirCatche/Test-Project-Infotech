package com.slobodyanyuk.testprojectinfotech.presentation.cities

import android.graphics.Bitmap
import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.City

data class CitiesState(
    val searchedCityRequest: String = "",
    val bitmaps: Pair<Bitmap?, Bitmap?> = null to null,
    val parsedCities: List<City> = listOf(),
    val filteredCities: List<City> = parsedCities, //  in order to show full list in case of empty string
)
