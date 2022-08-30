package com.slobodyanyuk.testprojectinfotech.domain.use_case.cities

import com.slobodyanyuk.testprojectinfotech.data.source.remote.ImageDownloaderService
import com.slobodyanyuk.testprojectinfotech.domain.ImageDownloaderDelegate

class DownloadImage: ImageDownloaderService by ImageDownloaderDelegate() {

    suspend operator fun invoke(url: String) = getBitmapFromUrl(url)
}