package com.slobodyanyuk.testprojectinfotech.domain.use_case.cities

import com.slobodyanyuk.testprojectinfotech.data.source.remote.CitiesJsonParserService
import com.slobodyanyuk.testprojectinfotech.domain.CitiesJsonParserDelegate
import com.squareup.moshi.Moshi

class ParseCitiesFromJson: CitiesJsonParserService by CitiesJsonParserDelegate() {

    suspend operator fun invoke(jsonString: String, moshi: Moshi) = parseToCities(jsonString, moshi)

}