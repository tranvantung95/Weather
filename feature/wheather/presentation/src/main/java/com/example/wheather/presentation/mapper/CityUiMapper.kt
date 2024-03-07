package com.example.wheather.presentation.mapper

import com.example.core.presentation.UiModelMapper
import com.example.wheather.domain.model.City
import com.example.wheather.presentation.model.CityUiModel

class CityUiMapper : UiModelMapper<City, CityUiModel> {
    override fun mapToUiLayerModel(businessModel: City): CityUiModel {
        return CityUiModel(
            name = businessModel.name,
            lat = businessModel.lat,
            lon = businessModel.lon
        )
    }
}