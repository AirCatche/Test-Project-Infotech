package com.slobodyanyuk.testprojectinfotech.presentation.cities

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.slobodyanyuk.testprojectinfotech.R
import com.slobodyanyuk.testprojectinfotech.base.BaseFragment
import com.slobodyanyuk.testprojectinfotech.base.extension.getStringFromAssetsJson
import com.slobodyanyuk.testprojectinfotech.base.extension.launchWhenStarted
import com.slobodyanyuk.testprojectinfotech.databinding.FragmentCitiesBinding
import dagger.hilt.android.AndroidEntryPoint
import invisible
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.onEach
import visible

@AndroidEntryPoint
class CitiesFragment : BaseFragment<FragmentCitiesBinding>(
    FragmentCitiesBinding::inflate,
) {

    private val jsonCitiesString by lazy { requireContext().getStringFromAssetsJson(JSON_FILE_NAME) }

    override val viewModel: CitiesViewModel by viewModels()

    private val cityAdapter by lazy {
        CityAdapter(
            listOf(),
        ) { cityId, cityName, lng, lat ->
            findNavController(requireView()).navigate(
                R.id.action_Cities_to_CityDetails,
                bundleOf(
                    CITY_ID to cityId,
                    CITY_NAME to cityName,
                    LONGITUDE to lng,
                    LATITUDE to lat
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        binding.apply {
            rvCities.apply {
                layoutManager = LinearLayoutManager(requireContext())
                //   viewModel.citiesState.filteredCities.let { cityAdapter.setData() }
                adapter = this@CitiesFragment.cityAdapter
            }
            binding.etCitiesSearch.text?.clear()
            etCitiesSearch.doAfterTextChanged {
                viewModel.onEvent(
                    CitiesEvent.SearchRequestChanged(
                        etCitiesSearch.text.toString().trim()
                    )
                )
            }
        }

        viewModel.onEvent(CitiesEvent.InitScreenData(jsonCitiesString))
    }

    private fun setupObservers() {

        viewModel.filteredCityItems.debounce(400L).onEach { items ->
            if (!(items.isEmpty()
                        .and(viewModel.citiesState.searchedCityRequest.isBlank()))
            ) { // in order to prevent setting default value with empty items after parsing city
                cityAdapter.setData(items)
            }
        }.launchWhenStarted(lifecycleScope)

        viewModel.parsedCityItems.drop(1).onEach { items ->
            cityAdapter.setData(items)
        }.launchWhenStarted(lifecycleScope)

        viewModel.isLoading.onEach {
            binding.rvCities.invisible(it)
            binding.pbCities.visible(it)
        }.launchWhenStarted(lifecycleScope)
    }

    override fun onDestroyView() {
        binding.rvCities.adapter = null
        super.onDestroyView()
    }

    companion object {

        const val JSON_FILE_NAME = "city_list.json"

        const val CITY_ID = "cityId"
        const val CITY_NAME = "cityName"
        const val LONGITUDE = "longitude"
        const val LATITUDE = "latitude"
    }
}