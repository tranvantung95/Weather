package com.example.wheather.domain.di

import com.example.wheather.domain.usecase.GetCityUseCase
import com.example.wheather.domain.usecase.GetForecastUseCase
import com.example.wheather.domain.usecase.GetWeatherUseCase
import org.koin.dsl.module

val weatherUseCaseModule = module {
    factory {
        GetCityUseCase(get())
    }
    factory {
        GetWeatherUseCase(get())
    }
    factory {
        GetForecastUseCase(get())
    }
}