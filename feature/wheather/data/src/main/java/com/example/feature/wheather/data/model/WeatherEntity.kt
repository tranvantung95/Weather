package com.example.feature.wheather.data.model

import com.example.core.data.EntityModel
import com.google.gson.annotations.SerializedName

data class WeatherEntity(
    val coord: Coord? = null,
    @SerializedName("weather")
    val weatherDetail: List<WeatherDetail>? = null,
    val base: String? = null,
    val main: Main? = null,
    val visibility: Long? = null,
    val wind: Wind? = null,
    val rain: Rain? = null,
    val clouds: Clouds? = null,
    val dt: Long? = null,
    val sys: Sys? = null,
    val timezone: Long? = null,
    val id: Long? = null,
    val name: String? = null,
    val cod: Long? = null,
) : EntityModel

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Long,
    val humidity: Long,
    @SerializedName("sea_level")
    val seaLevel: Long,
    @SerializedName("grnd_level")
    val grndLevel: Long,
)

data class Coord(
    val lon: Float,
    val lat: Float,
)

data class WeatherDetail(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

data class Wind(
    val speed: Double,
    val deg: Long,
    val gust: Double,
)

data class Rain(
    @SerializedName("1h")
    val n1h: Double,
)

data class Clouds(
    val all: Long,
)

data class Sys(
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long,
)