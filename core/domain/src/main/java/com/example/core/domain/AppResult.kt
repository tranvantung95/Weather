package com.example.core.domain

typealias ErrorEntity = Error

sealed class AppResult<out R> {
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Error(val error: ErrorEntity) : AppResult<Nothing>()
}