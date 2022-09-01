package com.slobodyanyuk.testprojectinfotech.data.mappers

import android.graphics.Bitmap
import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.City
import com.slobodyanyuk.testprojectinfotech.domain.entity.cities.ItemCity

fun List<City>.toCityItems(evenBitmap: Bitmap?, oddBitmap: Bitmap?) =
        this.mapIndexed { index, city ->
            if (index % 2 == 0) {
                ItemCity(city, evenBitmap)
            } else {
                ItemCity(city, oddBitmap)
            }
        }