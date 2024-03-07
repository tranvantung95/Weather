package com.example.wheather.presentation.mapper

import com.example.core.presentation.UiModelMapper
import com.example.feature.localstorage.domain.model.FavoriteCityCachingBusinessModel
import com.example.wheather.presentation.model.FavoriteCityUiModel

class CityCachingModelMapper :
    UiModelMapper<FavoriteCityCachingBusinessModel, FavoriteCityUiModel> {
    fun mapToBusinessModel(favoriteCityUiModel: FavoriteCityUiModel): FavoriteCityCachingBusinessModel {
        return FavoriteCityCachingBusinessModel(
            name = favoriteCityUiModel.name,
            lat = favoriteCityUiModel.lat,
            lon = favoriteCityUiModel.lon
        )
    }

    override fun mapToUiLayerModel(businessModel: FavoriteCityCachingBusinessModel): FavoriteCityUiModel {
        return FavoriteCityUiModel(
            name = businessModel.name,
            lat = businessModel.lat,
            lon = businessModel.lon
        )
    }
}
