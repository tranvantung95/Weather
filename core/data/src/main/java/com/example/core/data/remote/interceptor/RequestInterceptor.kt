package com.example.core.data.remote.interceptor


import com.example.core.data.BuildConfig.WEATHER_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originUrl = request.url
        val url =
            originUrl.newBuilder().addQueryParameter("appid", WEATHER_API_KEY).build()
        val requestBuilder = request.newBuilder().url(url)
        return chain.proceed(requestBuilder.build())
    }
}