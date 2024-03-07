package com.example.core.presentation

data class ErrorUiModel(val title: String, override val message: String) :
    Throwable(message = message)
