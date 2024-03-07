package com.example.feature.localstorage.data

import com.example.feature.localstorage.data.datasource.LocalDataSource
import com.example.feature.localstorage.data.mapper.FavoriteCityMapper
import com.example.feature.localstorage.domain.gateway.ICachingGateway
import com.example.feature.localstorage.domain.model.FavoriteCityCachingBusinessModel

class LocalStorageRepository(
    private val localDataSource: LocalDataSource,
    private val favoriteCityMapper: FavoriteCityMapper
) :
    ICachingGateway {
    companion object {
        const val CITY_FAVORITE_KEY = "CITY_FAVORITE_KEY"
    }

    override fun saveCityFavorite(favoriteCityCachingBusinessModel: FavoriteCityCachingBusinessModel) {
        localDataSource.saveDataToCache(
            CITY_FAVORITE_KEY,
            favoriteCityMapper.mapToEntityModel(favoriteCityCachingBusinessModel)
        )
    }

    override fun getFavorite(): List<FavoriteCityCachingBusinessModel>? {
        return  localDataSource.getCachedData(CITY_FAVORITE_KEY)?.map {
            favoriteCityMapper.invoke(it)
        }
    }
}