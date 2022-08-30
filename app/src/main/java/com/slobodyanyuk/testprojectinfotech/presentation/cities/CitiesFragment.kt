package com.slobodyanyuk.testprojectinfotech.presentation.cities

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.slobodyanyuk.testprojectinfotech.base.BaseFragment
import com.slobodyanyuk.testprojectinfotech.base.extension.getStringFromAssetsJson
import com.slobodyanyuk.testprojectinfotech.base.extension.launchWhenStarted
import com.slobodyanyuk.testprojectinfotech.databinding.FragmentCitiesBinding
import com.slobodyanyuk.testprojectinfotech.domain.entity.ItemCity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

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
        ) { cityId -> viewModel.onEvent(CitiesEvent.OnCityClicked(cityId)) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onEvent(CitiesEvent.InitDataLoading(jsonCitiesString))

        binding.apply {
            rvCities.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = this@CitiesFragment.cityAdapter
            }
            etSearch.doAfterTextChanged {
                viewModel.onEvent(CitiesEvent.SearchRequestChanged(etSearch.text.toString()))
            }
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.cityRequest.onEach { request ->
            cityAdapter.setData(
                viewModel.citiesState.parsedCities.map { city ->
                    ItemCity(city, viewModel.citiesState.bitmaps)
                }
            )
        }.launchWhenStarted(lifecycleScope)

        viewModel.cityItems.onEach { items ->
            cityAdapter.setData(items)
        }.launchWhenStarted(lifecycleScope)
    }

    companion object {

        const val JSON_FILE_NAME = "city_list.json"

        fun newInstance() = CitiesFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}