package com.slobodyanyuk.testprojectinfotech.data.source.remote

import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.City
import com.squareup.moshi.Moshi

interface CitiesJsonParserService {

    suspend fun parseToCities(jsonString: String, moshi: Moshi): List<City>?
}