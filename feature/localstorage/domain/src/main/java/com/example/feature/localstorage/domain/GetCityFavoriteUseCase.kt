package com.example.feature.localstorage.domain

import com.example.feature.localstorage.domain.gateway.ICachingGateway
import com.example.feature.localstorage.domain.model.FavoriteCityCachingBusinessModel

class GetCityFavoriteUseCase(private val iCachingGateway: ICachingGateway) {
    operator fun invoke(): List<FavoriteCityCachingBusinessModel> {
        return iCachingGateway.getFavorite() ?: listOf()
    }
}