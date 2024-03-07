package com.example.core.data

data class WeatherRetryPolicy(
    override val numRetries: Long = 4,
    override val delayMillis: Long = 400,
    override val delayFactor: Long = 2
) : RetryPolicy
interface RetryPolicy {
    val numRetries: Long
    val delayMillis: Long
    val delayFactor: Long
}