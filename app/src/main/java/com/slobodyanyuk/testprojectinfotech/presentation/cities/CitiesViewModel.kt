package com.slobodyanyuk.testprojectinfotech.presentation.cities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slobodyanyuk.testprojectinfotech.domain.entity.ItemCity
import com.slobodyanyuk.testprojectinfotech.domain.use_case.cities.CitiesUseCases
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val citiesUseCases: CitiesUseCases,
    private val moshi: Moshi,
) : ViewModel() {

    private var _citiesState = CitiesState()
    val citiesState = _citiesState

    private var _cityRequest = MutableStateFlow("")
    val cityRequest = _cityRequest.asStateFlow()

    private var _cityItems = MutableStateFlow<List<ItemCity>>(listOf())
    val cityItems = _cityItems.asStateFlow()

    fun onEvent(event: CitiesEvent) {
        when (event) {
            is CitiesEvent.OnCityClicked -> {
                //TODO City Details(Implement navigation to city details screen)
                event.cityId
            }
            is CitiesEvent.SearchRequestChanged -> {
                viewModelScope.launch {
                    _citiesState = _citiesState.copy(searchedCityRequest = event.cityRequest)
                }

                viewModelScope.launch {
                    val filteredCities = citiesUseCases.onRequestChanged(
                        _citiesState.parsedCities,
                        event.cityRequest
                    )
                    _citiesState = _citiesState.copy(filteredCities = filteredCities)
                    _cityItems.emit(
                        filteredCities.map {
                            ItemCity(it, _citiesState.bitmaps)
                        }
                    )
                }
            }

            is CitiesEvent.InitDataLoading -> {
                val parsedCities = viewModelScope.async {
                    citiesUseCases.parseCitiesFromJson(event.jsonString, moshi)?.let { cities ->
                        _citiesState = _citiesState.copy(parsedCities = cities)
                        cities
                    }
                }

                val downloadedBitmaps = viewModelScope.async(Dispatchers.Default) {
                    val evenBitmap = withContext(Dispatchers.Default) { // move to delegate for
                        citiesUseCases.downloadBitmap(EVEN_IMAGE_URL)
                    }
                    val oddBitmap = withContext(Dispatchers.Default) {
                        citiesUseCases.downloadBitmap(ODD_IMAGE_URL)
                    }
                    val bitmaps = evenBitmap to oddBitmap
                    _citiesState = _citiesState.copy(bitmaps = bitmaps)
                    bitmaps
                }

                viewModelScope.launch(Dispatchers.Main) {
                    val cityItems = parsedCities.await()?.map {
                        ItemCity(it, downloadedBitmaps.await())
                    }
                    cityItems?.let {
                        _cityItems.emit(it)
                    }
                }
            }
        }
    }

    companion object {

        const val ODD_IMAGE_URL = "https://infotech.gov.ua/storage/img/Temp1.png"
        const val EVEN_IMAGE_URL = "https://infotech.gov.ua/storage/img/Temp3.png"
    }
}