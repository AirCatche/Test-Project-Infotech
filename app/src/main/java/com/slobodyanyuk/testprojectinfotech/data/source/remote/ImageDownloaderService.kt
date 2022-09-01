package com.slobodyanyuk.testprojectinfotech.data.source.remote

import android.graphics.Bitmap

interface ImageDownloaderService {

    suspend fun getBitmapWithUrl(url: String): Bitmap?
}