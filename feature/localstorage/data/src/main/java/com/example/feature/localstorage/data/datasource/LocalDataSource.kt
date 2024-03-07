package com.example.feature.localstorage.data.datasource

import android.content.Context
import com.example.feature.localstorage.data.model.FavoriteCityCachingEntity
import com.example.feature.localstorage.data.objectToString
import com.example.feature.localstorage.data.stringToObject

class LocalDataSource(context: Context) {
    private val sharedPrefs =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun saveDataToCache(key: String, favoriteCityCachingEntity: FavoriteCityCachingEntity) {
        val currentList = getCachedData(key)?.toMutableList() ?: mutableListOf()
        currentList.add(favoriteCityCachingEntity)
        sharedPrefs.edit().putString(key, objectToString(currentList)).apply()
    }

    fun getCachedData(key: String): List<FavoriteCityCachingEntity>? {
        val json = sharedPrefs.getString(key, null).orEmpty()
        return stringToObject(json)
    }
}