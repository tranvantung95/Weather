package com.example.wheather

import android.app.Application
import com.example.core.data.remote.retrofit.retrofitModule
import com.example.feature.localstorage.data.di.localStorageMapperModule
import com.example.feature.localstorage.data.di.localStorageModule
import com.example.feature.localstorage.domain.di.localStorageUseCaseModule
import com.example.feature.wheather.data.di.weatherDataMapper
import com.example.feature.wheather.data.di.weatherDataModule
import com.example.wheather.domain.di.weatherUseCaseModule
import com.example.wheather.presentation.di.mapperUiModule
import com.example.wheather.presentation.di.weatherViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                retrofitModule,
                weatherDataModule,
                weatherUseCaseModule,
                weatherViewModelModule,
                mapperUiModule,
                weatherDataMapper,
                localStorageModule,
                localStorageUseCaseModule,
                localStorageMapperModule
            )
        }
    }
}