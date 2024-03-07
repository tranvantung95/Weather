package com.example.core.common

object UnitConvert {
    fun kenvinToC(data: Double): Double {
        return Math.round((data.minus(273.15)).times(100.0)).div(100.0)
    }
}