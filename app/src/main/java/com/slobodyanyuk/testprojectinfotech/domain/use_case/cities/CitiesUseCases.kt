package com.slobodyanyuk.testprojectinfotech.domain.use_case.cities

data class CitiesUseCases(
    val onRequestChanged: OnRequestChanged,
    val clickOnCity: ClickOnCity,
    val downloadBitmap: DownloadImage,
    val parseCitiesFromJson: ParseCitiesFromJson,
)
