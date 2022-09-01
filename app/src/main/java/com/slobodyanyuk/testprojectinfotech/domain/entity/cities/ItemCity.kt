package com.slobodyanyuk.testprojectinfotech.domain.entity.cities

import android.graphics.Bitmap
import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.City

data class ItemCity(
    val city: City,
    val bitmapImage: Bitmap?,
)
