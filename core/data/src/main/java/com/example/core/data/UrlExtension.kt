package com.example.core.data

fun createWeatherIconUrl(iconName: String): String {
    return BuildConfig.WEATHER_URL_ICON.replace(
        "{iconId}",
        iconName
    )
}