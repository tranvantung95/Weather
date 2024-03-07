package com.example.wheather.presentation.model

import com.example.core.presentation.UiModel
import com.github.mikephil.charting.data.LineData

data class ForecastUiModel(
    val forecastDetail: List<ForecastDetailUiModel>? = null,
    val temperatureLineData: LineData? = null,
    val humidityLineData: LineData? = null,
    val windLineData: LineData? = null,
) : UiModel
