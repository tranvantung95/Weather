package com.example.wheather.presentation

import com.example.core.presentation.ErrorUiModel
import com.example.wheather.presentation.model.CityUiModel

sealed interface CityState {
    data object Loading : CityState
    data class Error(val error: ErrorUiModel) : CityState
    data class Success(val data: List<CityUiModel>) : CityState
}