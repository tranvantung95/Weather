package com.example.feature.wheather.data.datasource

import com.example.feature.wheather.data.model.CityEntity
import com.example.feature.wheather.data.model.ForecastEntity
import com.example.feature.wheather.data.model.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDataSource {
    @GET("/geo/1.0/direct")
    suspend fun getCityByName(@Query("q") cityName: String): List<CityEntity>

    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("lat") lat: Float, @Query("lon") lon: Float): WeatherEntity

    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("cnt") cnt: Int
    ): ForecastEntity
}