package com.example.core.presentation

import androidx.lifecycle.ViewModel
import com.example.core.domain.AppResult
import kotlinx.coroutines.CoroutineDispatcher

open class BaseViewModel : ViewModel() {
    var dispatcher: CoroutineDispatcher? = null
    fun <T> AppResult<T>.safeCall(): T {
        return when (this) {
            is AppResult.Success -> {
                this.data
            }

            is AppResult.Error -> {
                throw ErrorUiModel(title = "Error", this.error.message)
            }
        }
    }

}