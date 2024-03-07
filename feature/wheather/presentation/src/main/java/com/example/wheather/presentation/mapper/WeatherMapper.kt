package com.example.wheather.presentation.mapper

import androidx.core.os.BuildCompat
import com.example.core.presentation.UiModelMapper
import com.example.wheather.domain.model.Weather
import com.example.wheather.presentation.model.WeatherUiModel

class WeatherMapper : UiModelMapper<Weather, WeatherUiModel> {
    override fun mapToUiLayerModel(businessModel: Weather): WeatherUiModel {
        return WeatherUiModel(
            temp = businessModel.temp,
            main = businessModel.main,
            humidity = businessModel.humidity,
            wind = businessModel.wind,
            feelsLike = businessModel.feelsLike,
            iconUrl = businessModel.iconUrl,
            cityName = businessModel.cityName
        )
    }
}