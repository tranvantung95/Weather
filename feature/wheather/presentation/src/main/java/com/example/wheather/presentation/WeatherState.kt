package com.example.wheather.presentation

import com.example.core.presentation.ErrorUiModel
import com.example.wheather.presentation.model.ForecastUiModel
import com.example.wheather.presentation.model.WeatherUiModel

sealed interface WeatherState {
    data object Loading : WeatherState
    data class Error(val error: ErrorUiModel) : WeatherState
    data class Success(var weathers: WeatherUiModel, val forecast : ForecastUiModel) : WeatherState
}