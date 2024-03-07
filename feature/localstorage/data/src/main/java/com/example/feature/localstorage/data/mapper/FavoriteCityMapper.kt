package com.example.feature.localstorage.data.mapper

import com.example.core.data.EntityMapper
import com.example.feature.localstorage.data.model.FavoriteCityCachingEntity
import com.example.feature.localstorage.domain.model.FavoriteCityCachingBusinessModel

class FavoriteCityMapper :
    EntityMapper<FavoriteCityCachingBusinessModel, FavoriteCityCachingEntity> {
    override fun invoke(entityModel: FavoriteCityCachingEntity): FavoriteCityCachingBusinessModel {
        return FavoriteCityCachingBusinessModel(
            name = entityModel.name,
            lat = entityModel.lat,
            lon = entityModel.lon
        )
    }

    fun mapToEntityModel(businessModel: FavoriteCityCachingBusinessModel): FavoriteCityCachingEntity {
        return FavoriteCityCachingEntity(
            name = businessModel.name,
            lat = businessModel.lat,
            lon = businessModel.lon
        )
    }
}