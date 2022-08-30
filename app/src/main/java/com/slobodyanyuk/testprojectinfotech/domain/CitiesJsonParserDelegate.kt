package com.slobodyanyuk.testprojectinfotech.domain

import com.slobodyanyuk.testprojectinfotech.data.source.remote.CitiesJsonParserService
import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.City
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class CitiesJsonParserDelegate : CitiesJsonParserService {

    override suspend fun parseToCities(jsonString: String, moshi: Moshi): List<City>? =
            moshi.adapter<List<City>>(
                Types.newParameterizedType(
                    List::class.java,
                    City::class.java
                )
            ).fromJson(jsonString)
}