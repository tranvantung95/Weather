package com.example.wheather.presentation.mapper

import com.example.core.presentation.UiModelMapper
import com.example.wheather.domain.model.Forecast
import com.example.wheather.presentation.model.ForecastDetailUiModel
import com.example.wheather.presentation.model.ForecastUiModel

class ForecastMapper(
    private val weatherMapper: WeatherMapper,
    private val forecastToLineDataMapper: ForecastToLineDataMapper
) : UiModelMapper<Forecast, ForecastUiModel> {
    override fun mapToUiLayerModel(businessModel: Forecast): ForecastUiModel {
        return ForecastUiModel(
            forecastDetail = businessModel.forecast?.map {
                ForecastDetailUiModel(
                    timeStamp = it.timeStamp,
                    weatherUiModel = it.weather?.let { weather ->
                        weatherMapper.mapToUiLayerModel(weather)
                    }
                )
            },
            temperatureLineData = forecastToLineDataMapper.mapToLineData(
                businessModel.forecast ?: listOf(), ForecastDataType.Temperature
            ),
            humidityLineData = forecastToLineDataMapper.mapToLineData(
                businessModel.forecast ?: listOf(), ForecastDataType.Humidity
            ),
            windLineData = forecastToLineDataMapper.mapToLineData(
                businessModel.forecast ?: listOf(), ForecastDataType.Wind
            )
        )
    }
}