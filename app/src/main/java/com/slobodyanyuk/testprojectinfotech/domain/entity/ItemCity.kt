package com.slobodyanyuk.testprojectinfotech.domain.entity

import android.graphics.Bitmap
import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.City

data class ItemCity(
    val city: City,
    val bitmapImage: Pair<Bitmap?, Bitmap?>,
)
