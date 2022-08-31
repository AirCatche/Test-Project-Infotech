package com.slobodyanyuk.testprojectinfotech.data.repository

import com.slobodyanyuk.testprojectinfotech.data.mappers.toWeatherInfo
import com.slobodyanyuk.testprojectinfotech.data.source.remote.WeatherApi
import com.slobodyanyuk.testprojectinfotech.domain.entity.city_detail.WeatherInfo
import com.slobodyanyuk.testprojectinfotech.domain.repository.WeatherRepository
import com.slobodyanyuk.testprojectinfotech.domain.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherById(
        cityId: Int,
        apiKey: String
    ): Resource<WeatherInfo> = try {

        Resource.Success(
            data = api.getWeatherById(
                cityId,
                apiKey
            ).toWeatherInfo()
        )
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Error(e.message ?: "Unknown error")
    }
}