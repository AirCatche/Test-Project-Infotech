package com.slobodyanyuk.testprojectinfotech.data.repository

import com.slobodyanyuk.testprojectinfotech.data.source.remote.CitiesJsonParserService
import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.City
import com.slobodyanyuk.testprojectinfotech.domain.CitiesJsonParserDelegate
import com.slobodyanyuk.testprojectinfotech.domain.repository.CityRepository
import com.squareup.moshi.Moshi
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val moshi: Moshi
) : CityRepository,
    CitiesJsonParserService by CitiesJsonParserDelegate() {

    override suspend fun getCities(jsonString: String): List<City>? =
            parseToCities(jsonString, moshi = moshi)
}