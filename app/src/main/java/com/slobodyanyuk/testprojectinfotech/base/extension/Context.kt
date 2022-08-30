package com.slobodyanyuk.testprojectinfotech.base.extension

import android.content.Context

fun Context.getStringFromAssetsJson(fileName: String) =
        assets.open(fileName).bufferedReader().use {
            it.readText()
        }