package com.example.core.data.remote.utils

import okhttp3.ResponseBody
import org.json.JSONObject

object ResponseErrorHandler {

    private const val MESSAGE_KEY = "message"
    private const val ERROR_KEY = "error"

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }
}