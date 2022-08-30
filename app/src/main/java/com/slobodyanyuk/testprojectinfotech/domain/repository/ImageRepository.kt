package com.slobodyanyuk.testprojectinfotech.domain.repository

import android.graphics.Bitmap

interface ImageRepository {

    suspend fun getBitmapFromUrl(url: String): Bitmap?

}