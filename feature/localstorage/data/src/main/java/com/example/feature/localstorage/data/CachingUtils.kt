package com.example.feature.localstorage.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

inline fun <reified T> stringToObject(json: String): T {
    val gson = GsonBuilder().setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create()
    return gson.fromJson(json, genericType<T>())
}

inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type
fun <TargetClass> objectToString(objectIn: TargetClass): String {
    return Gson().toJson(objectIn)
}