package com.example.wheather.presentation.model

import com.example.core.presentation.UiModel

data class ForecastDetailUiModel(
    val timeStamp: Long? = null,
    val weatherUiModel: WeatherUiModel? = null
): UiModel
