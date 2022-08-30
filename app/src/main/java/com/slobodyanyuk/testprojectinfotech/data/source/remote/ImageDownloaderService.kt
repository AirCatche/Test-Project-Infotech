package com.slobodyanyuk.testprojectinfotech.data.source.remote

import android.graphics.Bitmap

interface ImageDownloaderService {

    suspend fun getBitmapFromUrl(url: String): Bitmap?
}