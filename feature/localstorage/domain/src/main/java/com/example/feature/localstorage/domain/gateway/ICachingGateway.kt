package com.example.feature.localstorage.domain.gateway

import com.example.feature.localstorage.domain.model.FavoriteCityCachingBusinessModel

interface ICachingGateway {
    fun saveCityFavorite(favoriteCityCachingBusinessModel: FavoriteCityCachingBusinessModel)
    fun getFavorite(): List<FavoriteCityCachingBusinessModel>?
}