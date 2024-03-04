package com.example.feature.caching

import android.content.Context
import com.example.cachinggateway.ICachingFeature

class CachingDataFeatureImpl (
    context: Context
): ICachingFeature {
    private val sharedPrefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    override fun saveDataToCache(key: String, dataList: List<Any>) {
        //sharedPrefs.edit().putString(key, CachingUtils.fromObjToJsonCache(dataList)).apply()
    }

    override fun getCachedData(key: String): String? {
        return sharedPrefs.getString(key, null)
    }
}