package com.example.cachinggateway

interface ICachingFeature {
    fun saveDataToCache(key: String, dataList: List<Any>)
    fun getCachedData(key: String): String?
}