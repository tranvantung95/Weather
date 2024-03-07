package com.example.wheather.domain.usecase

import com.example.core.domain.AppResult
import com.example.wheather.domain.gateway.IWeatherGateway
import com.example.wheather.domain.model.Weather
import kotlinx.coroutines.flow.Flow


data class GetWeatherUseCaseParam(val lat: Float, val lon: Float)
fun interface IGetWeatherUseCase : (GetWeatherUseCaseParam) -> Flow<AppResult<Weather>>

class GetWeatherUseCase(private val iWeatherGateway: IWeatherGateway) : IGetWeatherUseCase {
    override fun invoke(param: GetWeatherUseCaseParam): Flow<AppResult<Weather>> {
        return iWeatherGateway.getWeather(lat = param.lat, lon = param.lon)
    }
}