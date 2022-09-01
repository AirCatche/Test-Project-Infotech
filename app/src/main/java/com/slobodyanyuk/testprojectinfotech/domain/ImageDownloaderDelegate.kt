package com.slobodyanyuk.testprojectinfotech.domain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.slobodyanyuk.testprojectinfotech.data.source.remote.ImageDownloaderService
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class ImageDownloaderDelegate : ImageDownloaderService {

    override suspend fun getBitmapWithUrl(url: String): Bitmap? =
            loadImageFromUrl(url)

    private suspend fun loadImageFromUrl(urlString: String): Bitmap? = try {
        BitmapFactory.decodeStream(getUrlFromString(urlString)?.openConnection()?.apply {
            doInput = true
            connect()
        }?.getInputStream())
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }

    private suspend fun getUrlFromString(urlString: String): URL? = try {
        URL(urlString)
    } catch (e: MalformedURLException) {
        e.printStackTrace()
        null
    }
}