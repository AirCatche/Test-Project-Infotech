package com.slobodyanyuk.testprojectinfotech.di

import com.slobodyanyuk.testprojectinfotech.data.repository.CityRepositoryImpl
import com.slobodyanyuk.testprojectinfotech.data.repository.ImageRepositoryImpl
import com.slobodyanyuk.testprojectinfotech.data.repository.WeatherRepositoryImpl
import com.slobodyanyuk.testprojectinfotech.domain.repository.CityRepository
import com.slobodyanyuk.testprojectinfotech.domain.repository.ImageRepository
import com.slobodyanyuk.testprojectinfotech.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        cityRepositoryImpl: CityRepositoryImpl
    ): CityRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}