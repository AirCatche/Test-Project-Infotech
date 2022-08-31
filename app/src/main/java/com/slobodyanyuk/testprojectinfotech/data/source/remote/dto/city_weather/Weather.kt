package com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.city_weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "description") val description: String
)

//{
//    "id": 800,
//    "main": "Clear",
//    "description": "clear sky",
//    "icon": "01d"
//}