package com.example.feature.localstorage.domain.model

import com.example.core.domain.BusinessModel

data class FavoriteCityCachingBusinessModel(val name: String, val lat: Float, val lon: Float) :
    BusinessModel
