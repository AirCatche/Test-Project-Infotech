package com.slobodyanyuk.testprojectinfotech.di

import com.slobodyanyuk.testprojectinfotech.base.Const.BASE_URL
import com.slobodyanyuk.testprojectinfotech.data.source.remote.WeatherApi
import com.slobodyanyuk.testprojectinfotech.domain.use_case.cities.CitiesUseCases
import com.slobodyanyuk.testprojectinfotech.domain.use_case.cities.ClickOnCity
import com.slobodyanyuk.testprojectinfotech.domain.use_case.cities.DownloadImage
import com.slobodyanyuk.testprojectinfotech.domain.use_case.cities.OnRequestChanged
import com.slobodyanyuk.testprojectinfotech.domain.use_case.cities.ParseCitiesFromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
            Moshi
                    .Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

    @Provides
    @Singleton
    fun provideCitiesUseCases() = CitiesUseCases(
        clickOnCity = ClickOnCity(),
        downloadBitmap = DownloadImage(),
        parseCitiesFromJson = ParseCitiesFromJson(),
        onRequestChanged = OnRequestChanged()
    )

}