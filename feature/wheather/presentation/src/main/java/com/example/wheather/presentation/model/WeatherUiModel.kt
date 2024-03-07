package com.example.wheather.presentation.model

import com.example.core.presentation.UiModel

data class WeatherUiModel(
    val temp: Double? = null,
    val feelsLike: Double? = null,
    val humidity: Long? = null,
    val wind: Double? = null,
    val main: String? = null,
    val iconUrl: String? = null,
    val cityName : String? = null
) : UiModel