package com.slobodyanyuk.testprojectinfotech.domain.use_case.cities

import com.slobodyanyuk.testprojectinfotech.domain.repository.ImageRepository

class DownloadImage(private val repository: ImageRepository) {

    suspend operator fun invoke(url: String) = repository.getBitmapFromUrl(url)
}