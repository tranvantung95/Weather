package com.example.feature.wheather.data.mapper

import com.example.core.common.UnitConvert
import com.example.core.data.EntityMapper
import com.example.core.data.createWeatherIconUrl
import com.example.feature.wheather.data.model.WeatherEntity
import com.example.wheather.domain.model.Weather

class WeatherMapper : EntityMapper<Weather, WeatherEntity> {
    override fun invoke(entityModel: WeatherEntity): Weather {
        return Weather(
            temp = UnitConvert.kenvinToC(entityModel.main?.temp ?: 0.0),
            feelsLike = entityModel.main?.feelsLike,
            humidity = entityModel.main?.humidity,
            main = entityModel.weatherDetail?.firstOrNull()?.main.orEmpty(),
            wind = entityModel.wind?.speed,
            iconUrl = createWeatherIconUrl(entityModel.weatherDetail?.firstOrNull()?.icon.orEmpty()),
            cityName = entityModel.name
        )
    }
}