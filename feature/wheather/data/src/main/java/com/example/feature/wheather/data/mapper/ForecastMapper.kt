package com.example.feature.wheather.data.mapper

import com.example.core.common.UnitConvert.kenvinToC

import com.example.core.data.EntityMapper
import com.example.core.data.createWeatherIconUrl
import com.example.feature.wheather.data.model.ForecastEntity
import com.example.wheather.domain.model.Forecast
import com.example.wheather.domain.model.ForecastData
import com.example.wheather.domain.model.Weather

class ForecastMapper(private val cityMapper: CityMapper) : EntityMapper<Forecast, ForecastEntity> {
    override fun invoke(entityModel: ForecastEntity): Forecast {
        val forecastEntity = entityModel.list
        return Forecast(city = entityModel.city?.let {
            cityMapper.invoke(it)
        }, forecast = forecastEntity?.map { forecast ->
            val weatherDetail = forecast.weatherDetail.firstOrNull()
            val main = forecast.main
            ForecastData(
                timeStamp = forecast.timeStamp, weather = Weather(
                    temp = kenvinToC(main?.temp ?: 0.0),
                    feelsLike = main?.feelsLike,
                    humidity = main?.humidity,
                    wind = forecast.wind?.speed,
                    iconUrl = createWeatherIconUrl(weatherDetail?.icon.orEmpty()),
                    main = weatherDetail?.main
                )
            )
        })
    }
}