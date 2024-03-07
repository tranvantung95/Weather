package com.example.feature.wheather.data.model

import com.example.core.data.EntityModel
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityEntity(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("local_names")
    val localName: JsonObject,
    @SerializedName("lat")
    @Expose
    val lat: Float,
    @SerializedName("lon")
    @Expose
    val lon: Float,
    @SerializedName("country")
    @Expose
    val country: String,
    @SerializedName("state")
    @Expose
    val state: String
) : EntityModel
