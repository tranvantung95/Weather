package com.example.wheather.presentation.mapper

import com.example.core.presentation.UiModelMapper
import com.example.feature.localstorage.domain.model.FavoriteCityCachingBusinessModel
import com.example.wheather.domain.model.FavoriteCity
import com.example.wheather.presentation.model.CityUiModel
import com.example.wheather.presentation.model.FavoriteCityUiModel

class FavoriteCityMapper : UiModelMapper<FavoriteCityCachingBusinessModel, FavoriteCityUiModel> {
    override fun mapToUiLayerModel(businessModel: FavoriteCityCachingBusinessModel): FavoriteCityUiModel {
        return FavoriteCityUiModel(
            name = businessModel.name,
            lat = businessModel.lat,
            lon = businessModel.lon
        )
    }
    fun mapCityModelToBusinessModel(uiModel : CityUiModel): FavoriteCityCachingBusinessModel{
        return  FavoriteCityCachingBusinessModel(
            name = uiModel.name,
            lat = uiModel.lat,
            lon = uiModel.lon
        )
    }
}