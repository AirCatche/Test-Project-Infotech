package com.slobodyanyuk.testprojectinfotech.presentation.city_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slobodyanyuk.testprojectinfotech.base.Const
import com.slobodyanyuk.testprojectinfotech.domain.entity.city_detail.WeatherInfo
import com.slobodyanyuk.testprojectinfotech.domain.use_case.city_details.CityDetailsUseCases
import com.slobodyanyuk.testprojectinfotech.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityDetailViewModel @Inject constructor(
    private val cityDetailsUseCases: CityDetailsUseCases
) : ViewModel() {

    private var _cityDetailsState = CityDetailsState()
    val cityDetailsState = _cityDetailsState

    private var _weatherInfo = MutableStateFlow(WeatherInfo())
    val weatherInfo = _weatherInfo.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun onEvent(event: CityDetailsEvent) {
        when (event) {
            is CityDetailsEvent.LoadWeatherInfo -> {
                viewModelScope.launch {
                    _isLoading.emit(true)
                    when (val result =
                            cityDetailsUseCases.loadWeatherData(event.cityId, Const.API_KEY)) {
                        is Resource.Success -> {
                            _cityDetailsState = _cityDetailsState.copy(
                                weatherInfo = result.data,
                                isLoading = false,
                                error = null,
                            )
                            _weatherInfo.emit(result.data!!)
                        }
                        is Resource.Error -> {
                            _cityDetailsState = _cityDetailsState.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                    _isLoading.emit(false)
                }
            }
        }
    }
}