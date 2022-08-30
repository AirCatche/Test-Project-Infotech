package com.slobodyanyuk.testprojectinfotech.domain.repository

import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.City

interface CityRepository {

    suspend fun getCities(jsonString: String): List<City>?

}