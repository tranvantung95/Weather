package com.example.feature.localstorage.data.di

import com.example.feature.localstorage.data.mapper.FavoriteCityMapper
import org.koin.dsl.module

val localStorageMapperModule = module {
    single {
        FavoriteCityMapper()
    }
}