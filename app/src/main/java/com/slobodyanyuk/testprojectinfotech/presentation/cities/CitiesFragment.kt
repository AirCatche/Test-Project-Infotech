package com.slobodyanyuk.testprojectinfotech.presentation.cities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.slobodyanyuk.testprojectinfotech.R
import com.slobodyanyuk.testprojectinfotech.base.BaseFragment
import com.slobodyanyuk.testprojectinfotech.base.extension.getStringFromAssetsJson
import com.slobodyanyuk.testprojectinfotech.base.extension.launchWhenStarted
import com.slobodyanyuk.testprojectinfotech.databinding.FragmentCitiesBinding
import com.slobodyanyuk.testprojectinfotech.domain.entity.cities.ItemCity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import visible

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class CitiesFragment : BaseFragment<FragmentCitiesBinding>(
    FragmentCitiesBinding::inflate,
) {

    private val jsonCitiesString by lazy { requireContext().getStringFromAssetsJson(JSON_FILE_NAME) }

    override val viewModel: CitiesViewModel by viewModels()

    private val cityAdapter by lazy {
        CityAdapter(
            listOf(),
        ) { cityId, cityName ->
            findNavController(requireView()).navigate(
                R.id.action_Cities_to_CityDetails,
                bundleOf("cityId" to cityId, "cityName" to cityName)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAGTAG", "onViewCreated: ")

        binding.apply {
            rvCities.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = this@CitiesFragment.cityAdapter
            }
            binding.etCitiesSearch.text?.clear()
            etCitiesSearch.doAfterTextChanged {
                viewModel.onEvent(CitiesEvent.SearchRequestChanged(etCitiesSearch.text.toString()))
            }
        }

        viewModel.onEvent(CitiesEvent.InitDataLoading(jsonCitiesString))

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.cityRequest.onEach { request ->
            cityAdapter.setData(
                viewModel.citiesState.filteredCities.map { city ->
                    ItemCity(city, viewModel.citiesState.bitmaps)
                }
            )
        }.launchWhenStarted(lifecycleScope)

        viewModel.cityItems.debounce(500L).onEach { items ->
            cityAdapter.setData(items)
        }.launchWhenStarted(lifecycleScope)

        viewModel.isLoading.onEach {
            binding.rvCities.visible(!it)
            binding.pbCities.visible(it)
        }.launchWhenStarted(lifecycleScope)
    }

    override fun onDestroyView() {
        binding.rvCities.adapter = null
        super.onDestroyView()
    }

    companion object {

        const val JSON_FILE_NAME = "city_list.json"
    }
}