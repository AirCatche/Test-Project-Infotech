package com.slobodyanyuk.testprojectinfotech.domain.use_case.cities

import com.slobodyanyuk.testprojectinfotech.data.source.remote.CitiesJsonParserService
import com.slobodyanyuk.testprojectinfotech.domain.CitiesJsonParserDelegate
import com.slobodyanyuk.testprojectinfotech.domain.repository.CityRepository

class ParseCitiesFromJson(private val repository: CityRepository) :
    CitiesJsonParserService by CitiesJsonParserDelegate() {

    suspend operator fun invoke(jsonString: String) = repository.getCities(jsonString)
}