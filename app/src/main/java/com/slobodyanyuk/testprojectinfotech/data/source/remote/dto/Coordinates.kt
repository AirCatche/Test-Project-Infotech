package com.slobodyanyuk.testprojectinfotech.data.source.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coordinates(
    @Json(name = "lon") val longitude: Float,
    @Json(name = "lat") val latitude: Float,
)
