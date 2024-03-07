package com.example.wheather.domain.model

import com.example.core.domain.BusinessModel

data class Weather(
    val temp: Double? = null,
    val feelsLike: Double? = null,
    val humidity: Long? = null,
    val wind: Double? = null,
    val main: String? = null,
    val iconUrl: String? = null,
    val cityName : String? = null
) : BusinessModel {
}