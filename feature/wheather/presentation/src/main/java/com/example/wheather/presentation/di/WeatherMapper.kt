package com.example.wheather.presentation.di

import com.example.wheather.presentation.mapper.CityCachingModelMapper
import com.example.wheather.presentation.mapper.CityUiMapper
import com.example.wheather.presentation.mapper.FavoriteCityMapper
import com.example.wheather.presentation.mapper.ForecastMapper
import com.example.wheather.presentation.mapper.ForecastToLineDataMapper
import com.example.wheather.presentation.mapper.WeatherMapper
import org.koin.dsl.module

val mapperUiModule = module {
    factory {
        CityUiMapper()
    }
    factory {
        WeatherMapper()
    }
    factory {
        ForecastMapper(get(), get())
    }
    factory {
        ForecastToLineDataMapper()
    }
    factory {
        FavoriteCityMapper()
    }
    factory {
        CityCachingModelMapper()
    }
}