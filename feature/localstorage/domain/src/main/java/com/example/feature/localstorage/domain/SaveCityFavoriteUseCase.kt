package com.example.feature.localstorage.domain

import com.example.feature.localstorage.domain.gateway.ICachingGateway
import com.example.feature.localstorage.domain.model.FavoriteCityCachingBusinessModel

class SaveCityFavoriteUseCase(private val iCachingGateway: ICachingGateway) {
    operator fun invoke(favoriteCityCachingBusinessModel: FavoriteCityCachingBusinessModel) {
        iCachingGateway.saveCityFavorite(favoriteCityCachingBusinessModel)
    }
}