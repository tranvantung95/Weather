package com.example.wheather.domain.gateway

import com.example.core.domain.AppResult
import com.example.wheather.domain.model.City
import com.example.wheather.domain.model.Forecast
import com.example.wheather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface IWeatherGateway {
    fun searchCityByName(cityName: String): Flow<AppResult<List<City>>>
    fun getWeather(lat: Float, lon: Float): Flow<AppResult<Weather>>
    fun getForecast(lat: Float, lon: Float, samplePoint: Int): Flow<AppResult<Forecast>>
}