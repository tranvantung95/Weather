package com.example.wheather.domain.model

import com.example.core.domain.BusinessModel

data class Forecast(
    val city: City?,
    val forecast: List<ForecastData>? = null ) : BusinessModel
data class ForecastData(val timeStamp: Long? = null , val weather: Weather? = null ) : BusinessModel