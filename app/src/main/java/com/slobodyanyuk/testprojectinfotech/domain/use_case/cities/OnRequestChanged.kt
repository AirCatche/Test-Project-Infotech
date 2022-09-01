package com.slobodyanyuk.testprojectinfotech.domain.use_case.cities

import com.slobodyanyuk.testprojectinfotech.data.source.remote.dto.City

class OnRequestChanged {

    operator fun invoke(cities: List<City>, request: String) = cities.filter {
        it.name.startsWith(request)
    }
}