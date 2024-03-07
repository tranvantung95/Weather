package com.example.wheather.presentation.mapper

import android.graphics.Color
import com.example.wheather.domain.model.ForecastData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class ForecastToLineDataMapper {
     fun mapToLineData(forecastData: List<ForecastData>, type: ForecastDataType): LineData {
        val firstSamplePoint = forecastData.firstOrNull()?.timeStamp?.toFloat() ?: 0F
        val entry = forecastData.map { forecast ->
            when (type) {
                is ForecastDataType.Humidity -> {
                    Entry(
                        forecast.timeStamp?.minus(firstSamplePoint) ?: 0f,
                        forecast.weather?.humidity?.toFloat() ?: 0f
                    )
                }

                is ForecastDataType.Temperature -> {
                    Entry(
                        forecast.timeStamp?.minus(firstSamplePoint) ?: 0f,
                        forecast.weather?.temp?.toFloat() ?: 0f
                    )
                }

                is ForecastDataType.Wind -> {
                    Entry(
                        forecast.timeStamp?.minus(firstSamplePoint) ?: 0f,
                        forecast.weather?.wind?.toFloat() ?: 0f
                    )
                }
            }
        }
        val lineDataSet = LineDataSet(entry, type::class.java.simpleName).apply {
            this.mode =LineDataSet.Mode.CUBIC_BEZIER
            val color = when (type) {
                ForecastDataType.Temperature -> {
                    Color.RED
                }

                ForecastDataType.Wind -> {
                    Color.BLUE
                }

                else -> {
                    Color.GREEN
                }
            }
            this.color = color
            this.circleHoleColor = color
        }
        return LineData(lineDataSet)
    }
}

sealed interface ForecastDataType {
    object Humidity : ForecastDataType
    object Temperature : ForecastDataType
    object Wind : ForecastDataType
}