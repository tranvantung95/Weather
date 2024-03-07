package com.example.feature.wheather.data.model

import com.example.core.data.EntityModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastEntity(
    @SerializedName("cod")
    @Expose
    var cod: String? = null,
    @SerializedName("message")
    @Expose
    var message: Int? = null,
    @SerializedName("cnt")
    @Expose
    var cnt: Int? = null,
    @SerializedName("list")
    @Expose
    var list: List<ForecastListEntity>? = null,
    @SerializedName("city")
    @Expose
    var city: CityEntity? = null
) : EntityModel

data class ForecastListEntity(
    @SerializedName("dt")
    var timeStamp: Long? = null,
    @SerializedName("main")
    var main: Main? = null,
    @SerializedName("weather")
    var weatherDetail: List<WeatherDetail> = listOf(),
    @SerializedName("clouds")
    var clouds: Clouds? = null,
    @SerializedName("wind")
    var wind: Wind? = null,
    @SerializedName("visibility")
    var visibility: Int? = null,
    @SerializedName("pop")
    var pop: Float? = null,
    @SerializedName("sys")
    var sys: Sys? = null,
    @SerializedName("dt_txt")
    var dtTxt: String? = null
)