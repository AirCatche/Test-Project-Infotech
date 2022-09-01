package com.slobodyanyuk.testprojectinfotech.presentation.cities

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slobodyanyuk.testprojectinfotech.data.mappers.toCityItems
import com.slobodyanyuk.testprojectinfotech.data.source.local.CityImagesCache
import com.slobodyanyuk.testprojectinfotech.domain.entity.cities.ItemCity
import com.slobodyanyuk.testprojectinfotech.domain.use_case.cities.CitiesUseCases
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val citiesUseCases: CitiesUseCases,
) : ViewModel() {

    private var _citiesState = CitiesState()
    val citiesState = _citiesState

    private var _filteredCityItems = MutableStateFlow<List<ItemCity>>(listOf())
    val filteredCityItems = _filteredCityItems.asStateFlow()

    private var _parsedCityItems = MutableStateFlow<List<ItemCity>>(listOf())
    val parsedCityItems = _parsedCityItems.asStateFlow()

    private var _channel = Channel<Boolean>()
    val channel = _channel.consumeAsFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun onEvent(event: CitiesEvent) {
        when (event) {
            is CitiesEvent.SearchRequestChanged -> {
                viewModelScope.launch(Dispatchers.Default) {
                    _isLoading.emit(true)
                    _citiesState = _citiesState.copy(searchedCityRequest = event.cityRequest)

                    citiesUseCases.onRequestChanged(
                        _citiesState.parsedCities,
                        event.cityRequest
                    ).also {
                        _citiesState = _citiesState.copy(filteredCities = it)
                    }.toCityItems(_citiesState.bitmaps.first, _citiesState.bitmaps.second).also {
                        _isLoading.emit(false)
                        _filteredCityItems.emit(it)
                    }
                }
            }

            is CitiesEvent.InitScreenData -> {
                val cachedImages = getCachedImages(
                    CityImagesCache.cache[EVEN_IMAGE_CACHE],
                    CityImagesCache.cache[ODD_IMAGE_CACHE]
                )
                if (_citiesState.searchedCityRequest.isNotBlank()) {
                    viewModelScope.launch(Dispatchers.Default) {
                        _isLoading.emit(true)
                        citiesUseCases.onRequestChanged(
                            _citiesState.parsedCities,
                            _citiesState.searchedCityRequest
                        ).also {
                            _citiesState = _citiesState.copy(filteredCities = it)
                        }.toCityItems(
                            _citiesState.bitmaps.first,
                            _citiesState.bitmaps.second
                        ).also {
                            _filteredCityItems.emit(it)
                            _isLoading.emit(false)
                        }
                    }
                } else {
                    viewModelScope.launch {
                        _isLoading.emit(true)
                        val parsedCities =
                                withContext(viewModelScope.coroutineContext + Dispatchers.Default) {
                                    citiesUseCases.parseCitiesFromJson(event.jsonString)
                                            ?.let { cities ->
                                                _citiesState =
                                                        _citiesState.copy(parsedCities = cities)
                                                cities
                                            }
                                }
                        val downloadedBitmaps = cachedImages
                            ?: withContext(viewModelScope.coroutineContext + Dispatchers.Default) {
                                val evenBitmap =
                                        withContext(viewModelScope.coroutineContext + Dispatchers.Default) {
                                            citiesUseCases.downloadBitmap(EVEN_IMAGE_URL)
                                        }
                                val oddBitmap =
                                        withContext(viewModelScope.coroutineContext + Dispatchers.Default) {
                                            citiesUseCases.downloadBitmap(ODD_IMAGE_URL)
                                        }
                                saveOddBitmapToCache(oddBitmap)
                                saveEvenBitmapToCache(evenBitmap)

                                (evenBitmap to oddBitmap).also {
                                    _citiesState = _citiesState.copy(bitmaps = it)
                                }
                            }

                        viewModelScope.launch(Dispatchers.Main) {
                            parsedCities?.toCityItems(
                                downloadedBitmaps.first,
                                downloadedBitmaps.second
                            )?.also {
                                _parsedCityItems.emit(it)
                                _isLoading.emit(false)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveOddBitmapToCache(bitmap: Bitmap?) = bitmap?.let {
        CityImagesCache.cache[ODD_IMAGE_CACHE] = it
    }

    private fun saveEvenBitmapToCache(bitmap: Bitmap?) = bitmap?.let {
        CityImagesCache.cache[EVEN_IMAGE_CACHE] = bitmap
    }

    private fun getCachedImages(evenBitmap: Bitmap?, oddBitmap: Bitmap?): Pair<Bitmap, Bitmap>? =
            if (evenBitmap != null && oddBitmap != null) {
                evenBitmap to oddBitmap
            } else null

    companion object {

        const val ODD_IMAGE_URL = "https://infotech.gov.ua/storage/img/Temp1.png"
        const val EVEN_IMAGE_URL = "https://infotech.gov.ua/storage/img/Temp3.png"

        const val ODD_IMAGE_CACHE = "odd image cache"
        const val EVEN_IMAGE_CACHE = "even image cache"
    }
}