package com.example.core.domain

data class Error(
    val code: Int? = null,
    val message: String = "",
    val errorType: ErrorType
)
