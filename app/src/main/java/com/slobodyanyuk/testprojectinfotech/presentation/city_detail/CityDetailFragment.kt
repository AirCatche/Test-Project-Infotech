package com.slobodyanyuk.testprojectinfotech.presentation.city_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.slobodyanyuk.testprojectinfotech.R
import com.slobodyanyuk.testprojectinfotech.base.BaseFragment
import com.slobodyanyuk.testprojectinfotech.base.extension.launchWhenStarted
import com.slobodyanyuk.testprojectinfotech.databinding.FragmentCityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import visible

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class CityDetailFragment : BaseFragment<FragmentCityDetailsBinding>(
    FragmentCityDetailsBinding::inflate,
) {

    override val viewModel: CityDetailViewModel by viewModels()

    private val temperatureFormatter by lazy {
        requireContext().getString(R.string.temperature_formatter)
    }
    private val humidityFormatter by lazy {
        requireContext().getString(R.string.humidity_formatter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCityDetailsName.text = arguments?.getString(CITY_NAME, "")
        viewModel.onEvent(
            CityDetailsEvent.LoadWeatherInfo(
                cityId = arguments?.getInt(CITY_ID, 0) ?: 0
            )
        )
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.weatherInfo.onEach { item ->
            with(binding) {
                tvCityDetailsDescription.text = item.description
                tvCityDetailsCurrentTemp.text = temperatureFormatter.format(item.currentTemp)
                tvCityDetailsMinTemp.text = temperatureFormatter.format(item.minTemp)
                tvCityDetailsMaxTemp.text = temperatureFormatter.format(item.maxTemp)
                tvCityDetailsWindSpeed.text = item.windSpeed.toString()
                tvCityDetailsHumidity.text = humidityFormatter.format(item.humidity)
            }
        }.launchWhenStarted(lifecycleScope)
        viewModel.isLoading.onEach { isLoading ->
            with(binding) {
                pbCityDetails.visible(isLoading)
                gCityDetailsWeatherInfo.visible(!isLoading)
            }
        }.launchWhenStarted(lifecycleScope)
    }

    companion object {

        const val CITY_ID = "cityId"
        const val CITY_NAME = "cityName"
    }
}