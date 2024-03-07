package com.example.feature.localstorage.data.di


import com.example.feature.localstorage.data.LocalStorageRepository
import com.example.feature.localstorage.data.datasource.LocalDataSource
import com.example.feature.localstorage.domain.gateway.ICachingGateway
import org.koin.dsl.module

val localStorageModule = module {
    single<ICachingGateway> {
        LocalStorageRepository(get(), get())
    }
    factory {
        LocalDataSource(get())
    }
    factory {
        LocalStorageRepository(get(), get())
    }
}