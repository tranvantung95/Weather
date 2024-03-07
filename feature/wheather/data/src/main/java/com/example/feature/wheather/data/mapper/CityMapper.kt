package com.example.feature.wheather.data.mapper

import com.example.core.data.EntityMapper
import com.example.feature.wheather.data.model.CityEntity
import com.example.wheather.domain.model.City

class CityMapper : EntityMapper<City, CityEntity> {
    override fun invoke(entityModel: CityEntity): City {
        return City(name = entityModel.name, lat = entityModel.lat, lon = entityModel.lon)
    }
}