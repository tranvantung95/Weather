package com.example.wheather.domain.usecase

import com.example.core.domain.AppResult
import com.example.wheather.domain.gateway.IWeatherGateway
import com.example.wheather.domain.model.Weather
import kotlinx.coroutines.flow.Flow


data class GetWeatherUseCaseParam(val lat: Long)
fun interface IGetWeatherUseCase : (GetWeatherUseCaseParam) -> Flow<AppResult<Weather>>
class GetWeatherUseCase(val iWeatherGateway: IWeatherGateway) : IGetWeatherUseCase {
    override fun invoke(param: GetWeatherUseCaseParam): Flow<AppResult<Weather>> {
        TODO("Not yet implemented")
    }
}