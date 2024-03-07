package com.example.data.mockdata

import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object MockDataSource {
    private fun getJsonFromResource(jsonFileName: String): String {
        val url = this::class.java.classLoader?.getResource(jsonFileName)
        return url?.readText().orEmpty()
    }

    fun getMockCityBusinessModelJson(): String {
        return getJsonFromResource("city_business_model.json")
    }

    fun getMockWeatherBusinessModelJson(): String {
        return getJsonFromResource("weather_business_model.json")
    }

    fun getMockForecastBusinessModelJson(): String {
        return getJsonFromResource("forecast_business_model.json")
    }

    fun getMockFavoriteCityBusinessModelJson(): String {
        return getJsonFromResource("favorite_city_business_model.json")
    }
    fun getMockCityEntityJson(): String{
        val json  =getJsonFromResource("city_entity_response.json")
        return  getJsonFromResource("city_entity_response.json")
    }
    fun  getMockWeatherEntityJson(): String{
        return  getJsonFromResource("weather_entity.json")
    }
    fun getMockForecastEntityJson() : String{
        return  getJsonFromResource("forecast_entity.json")
    }
}

inline fun <reified T> stringToObject(json: String): T {
    val gson = GsonBuilder().setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create()
    return gson.fromJson(json, genericType<T>())
}

inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type
