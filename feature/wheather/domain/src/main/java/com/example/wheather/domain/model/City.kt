package com.example.wheather.domain.model

import com.example.core.domain.BusinessModel

data class City(val name: String, val lat: Float, val lon: Float) : BusinessModel
