package com.example.feature.wheather.data.di

import com.example.feature.wheather.data.mapper.CityMapper
import com.example.feature.wheather.data.mapper.ForecastMapper
import com.example.feature.wheather.data.mapper.WeatherMapper
import org.koin.dsl.module

val weatherDataMapper = module {
    factory {
        CityMapper()
    }
    factory {
        ForecastMapper(get())
    }
    factory {
        WeatherMapper()
    }
}