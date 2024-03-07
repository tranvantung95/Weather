package com.example.feature.wheather.data.di

import com.example.core.data.WeatherRetryPolicy
import com.example.feature.wheather.data.datasource.WeatherDataSource
import com.example.feature.wheather.data.repository.WeatherRepository
import com.example.wheather.domain.gateway.IWeatherGateway
import org.koin.dsl.module
import retrofit2.Retrofit

val weatherDataModule = module {
    fun provideApiModule(retrofit: Retrofit): WeatherDataSource {
        return retrofit.create(WeatherDataSource::class.java)
    }

    single {
        provideApiModule(get())
    }
    factory {
        WeatherRepository(get(), get(), get(), get())
    }
    single<IWeatherGateway> {
        WeatherRepository(get(), get(), get(), get())
    }
    single {
        WeatherRetryPolicy()
    }
}
