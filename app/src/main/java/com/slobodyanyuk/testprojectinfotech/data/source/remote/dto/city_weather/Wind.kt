package com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.city_weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed") val speed: Float,
    @Json(name = "deg") val deg: Int,
)

//"wind": {
//    "speed": 1.5,
//    "deg": 350
//},