package com.example.wheather.domain.usecase

import com.example.core.domain.AppResult
import com.example.wheather.domain.gateway.IWeatherGateway
import com.example.wheather.domain.model.City
import kotlinx.coroutines.flow.Flow

data class GetCityUseCaseParam(val name: String)

fun interface IGetCityUseCase : (GetCityUseCaseParam) -> Flow<AppResult<List<City>>>

class GetCityUseCase(private val iWeatherGateway: IWeatherGateway) : IGetCityUseCase {
    override fun invoke(param: GetCityUseCaseParam): Flow<AppResult<List<City>>> {
        return iWeatherGateway.searchCityByName(cityName = param.name)
    }
}