package com.slobodyanyuk.testprojectinfotech.data.source.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    @Json(name = "id")val id: Int,
    @Json(name = "name")val name: String,
    @Json(name = "state")val state: String?,
    @Json(name = "country")val country: String?,
    @Json(name = "coord")val coord: Coordinates?,
)
