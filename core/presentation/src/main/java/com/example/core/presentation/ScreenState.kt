package com.example.core.presentation

sealed interface ScreenState {
    data object Loading : ScreenState
    data class Error(val error: ErrorUiModel) : ScreenState
}