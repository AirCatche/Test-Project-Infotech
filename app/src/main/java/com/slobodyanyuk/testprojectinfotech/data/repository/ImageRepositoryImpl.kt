package com.slobodyanyuk.testprojectinfotech.data.repository

import android.graphics.Bitmap
import com.slobodyanyuk.testprojectinfotech.data.source.remote.ImageDownloaderService
import com.slobodyanyuk.testprojectinfotech.domain.ImageDownloaderDelegate
import com.slobodyanyuk.testprojectinfotech.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor() : ImageRepository,
    ImageDownloaderService by ImageDownloaderDelegate() {

    override suspend fun getBitmapFromUrl(url: String): Bitmap? = getBitmapWithUrl(url)
}