package com.slobodyanyuk.testprojectinfotech.presentation.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slobodyanyuk.testprojectinfotech.domain.entity.cities.ItemCity
import com.slobodyanyuk.testprojectinfotech.domain.use_case.cities.CitiesUseCases
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun onEvent(event: CitiesEvent) {
        when (event) {
            is CitiesEvent.SearchRequestChanged -> {
                viewModelScope.launch {
                    _isLoading.emit(true)
                    _citiesState = _citiesState.copy(searchedCityRequest = event.cityRequest)
                }

                viewModelScope.launch(Dispatchers.Default) {
                    val filteredCities = citiesUseCases.onRequestChanged(
                        _citiesState.parsedCities,
                        event.cityRequest
                    )
                    _citiesState = _citiesState.copy(filteredCities = filteredCities)
                    val items = filteredCities.map {
                        ItemCity(it, _citiesState.bitmaps)
                    }
                    _isLoading.emit(false)
                    _cityItems.emit(items)
                }
            }

            is CitiesEvent.InitDataLoading -> {
                if (_citiesState.searchedCityRequest.isNotBlank()) {
                    viewModelScope.launch(Dispatchers.Default) {
                        _isLoading.emit(true)
                        val filteredCities = citiesUseCases.onRequestChanged(
                            _citiesState.parsedCities,
                            _citiesState.searchedCityRequest
                        )
                        _citiesState = _citiesState.copy(filteredCities = filteredCities)
                        val items = filteredCities.map {
                            ItemCity(it, _citiesState.bitmaps)
                        }
                        _cityItems.emit(items)
                        _isLoading.emit(false)
                    }
                } else {
                    val time = System.currentTimeMillis()
                    viewModelScope.launch {
                        _isLoading.emit(true)
                        val parsedCities = withContext(viewModelScope.coroutineContext) {
                            citiesUseCases.parseCitiesFromJson(event.jsonString, moshi)
                                    ?.let { cities ->
                                        _citiesState = _citiesState.copy(parsedCities = cities)
                                        cities
                                    }
                        }
                        val downloadedBitmaps =
                                withContext(viewModelScope.coroutineContext + Dispatchers.Default) {
                                    val evenBitmap =
                                            withContext(Dispatchers.Default) { // move to delegate for
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
                            val cityItems = parsedCities?.map {
                                ItemCity(it, downloadedBitmaps)
                            }
                            cityItems?.let {
                                _cityItems.emit(it)
                            }
                        }
                        _isLoading.emit(false)
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