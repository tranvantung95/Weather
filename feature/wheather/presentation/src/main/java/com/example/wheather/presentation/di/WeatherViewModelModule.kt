package com.example.wheather.presentation.di

import com.example.wheather.presentation.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherViewModelModule = module {
    viewModel {
        WeatherViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get())
    }
}