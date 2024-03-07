package com.example.wheather.domain.usecase

import com.example.core.domain.AppResult
import com.example.wheather.domain.gateway.IWeatherGateway
import com.example.wheather.domain.model.Forecast
import com.example.wheather.domain.model.Weather
import kotlinx.coroutines.flow.Flow


data class GetForecastUseCaseParam(val lat: Float, val lon: Float, val numberOfSample: Int)
fun interface IGetForecastUseCase : (GetForecastUseCaseParam) -> Flow<AppResult<Forecast>>
class GetForecastUseCase(private val iWeatherGateway: IWeatherGateway) : IGetForecastUseCase {
    override fun invoke(param: GetForecastUseCaseParam): Flow<AppResult<Forecast>> {
        return iWeatherGateway.getForecast(
            lat = param.lat,
            lon = param.lon,
            samplePoint = param.numberOfSample
        )
    }
}