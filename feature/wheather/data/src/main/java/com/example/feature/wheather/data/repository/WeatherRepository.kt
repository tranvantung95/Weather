package com.example.feature.wheather.data.repository

import com.example.core.data.safeApiCall
import com.example.core.domain.AppResult
import com.example.feature.wheather.data.datasource.WeatherDataSource
import com.example.feature.wheather.data.mapper.CityMapper
import com.example.feature.wheather.data.mapper.ForecastMapper
import com.example.feature.wheather.data.mapper.WeatherMapper
import com.example.feature.wheather.data.model.CityEntity
import com.example.wheather.domain.gateway.IWeatherGateway
import com.example.wheather.domain.model.City
import com.example.wheather.domain.model.Forecast
import com.example.wheather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    private val weatherDataSource: WeatherDataSource,
    private val forecastMapper: ForecastMapper,
    private val cityMapper: CityMapper,
    private val weatherMapper: WeatherMapper
) : IWeatherGateway {

    private val cityMappers: (List<CityEntity>) -> List<City> = {
        it.map { cityEntity ->
            cityMapper.invoke(cityEntity)
        }
    }

    override fun searchCityByName(cityName: String): Flow<AppResult<List<City>>> {
        return safeApiCall(cityMappers, {
            weatherDataSource.getCityByName(cityName)
        })
    }

    override fun getWeather(
        lat: Float,
        lon: Float
    ): Flow<AppResult<Weather>> {
        return safeApiCall({ weatherMapper.invoke(it) }, {
            weatherDataSource.getWeather(lat = lat, lon = lon)
        })
    }

    override fun getForecast(lat: Float, lon: Float, samplePoint: Int): Flow<AppResult<Forecast>> {
        return safeApiCall({
            forecastMapper.invoke(it)
        }, {
            weatherDataSource.getForecast(lat = lat, lon = lon, cnt = samplePoint)
        })
    }

}