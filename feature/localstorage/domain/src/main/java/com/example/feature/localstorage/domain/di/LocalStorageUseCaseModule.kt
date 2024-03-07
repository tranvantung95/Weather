package com.example.feature.localstorage.domain.di

import com.example.feature.localstorage.domain.GetCityFavoriteUseCase
import com.example.feature.localstorage.domain.SaveCityFavoriteUseCase
import org.koin.dsl.module

val localStorageUseCaseModule = module {
    factory {
        GetCityFavoriteUseCase(get())
    }
    factory {
        SaveCityFavoriteUseCase(get())
    }
}