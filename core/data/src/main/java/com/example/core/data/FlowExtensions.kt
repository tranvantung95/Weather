package com.example.core.data


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

fun <T> Flow<T>.retryWithPolicy(
    retryPolicy: RetryPolicy
): Flow<T> {
    var currentDelay = retryPolicy.delayMillis
    val delayFactor = retryPolicy.delayFactor
    return retryWhen { cause, attempt ->
        if ((cause is IOException) && attempt < retryPolicy.numRetries) {
            println("retry.......${attempt}")
            delay(currentDelay)
            currentDelay *= delayFactor
            return@retryWhen true
        } else {
            return@retryWhen false
        }
    }
}