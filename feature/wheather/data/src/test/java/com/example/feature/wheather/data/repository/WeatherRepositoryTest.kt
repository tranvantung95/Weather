package com.example.feature.wheather.data.repository

import app.cash.turbine.turbineScope
import com.example.core.data.remote.NullOnEmptyConverterFactory
import com.example.core.domain.AppResult
import com.example.data.mockdata.MockDataSource
import com.example.feature.wheather.data.datasource.WeatherDataSource
import com.example.feature.wheather.data.mapper.CityMapper
import com.example.feature.wheather.data.mapper.ForecastMapper
import com.example.feature.wheather.data.mapper.WeatherMapper
import com.example.unitest.BaseUnitTest
import com.example.wheather.domain.model.City
import com.example.wheather.domain.model.Forecast
import com.example.wheather.domain.model.Weather
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class WeatherRepositoryTest : BaseUnitTest() {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var weatherDataSource: WeatherDataSource
    private lateinit var weatherRepository: WeatherRepository
    private val cityMapper: CityMapper by lazy {
        CityMapper()
    }
    private val forecastMapper: ForecastMapper by lazy {
        ForecastMapper(cityMapper)
    }
    private val weatherMapper: WeatherMapper by lazy {
        WeatherMapper()
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    override fun setUp() {
        super.setUp()
        initMockWebsever()
        weatherRepository =
            WeatherRepository(weatherDataSource, forecastMapper, cityMapper, weatherMapper)
    }

    override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

    private fun initMockWebsever() {
        MockKAnnotations.init(this)
        mockWebServer = MockWebServer()
        mockWebServer.start(8000)
        weatherDataSource = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                        .enableComplexMapKeySerialization()
                        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                        .setPrettyPrinting()
                        .setVersion(1.0)
                        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
                )
            )
            .client(
                OkHttpClient.Builder().apply {
                    addInterceptor(HttpLoggingInterceptor())
                }.build()
            )
            .build()
            .create(WeatherDataSource::class.java)
    }

    private fun setUpResponse(responseCode: Int, bodyString: String? = null): MockResponse {
        return MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setResponseCode(responseCode)
            .setBody(bodyString.orEmpty())
    }

    @Test
    fun getWeatherSuccess() = runTest {
        val response =
            setUpResponse(HttpURLConnection.HTTP_OK, MockDataSource.getMockWeatherEntityJson())
        mockWebServer.enqueue(response)
        turbineScope {
            val turbine =
                weatherRepository.getWeather(0f, 0f).testIn(backgroundScope)
            Assert.assertEquals(
                (turbine.awaitItem() as AppResult.Success<Weather>).data.temp, -273.15
            )
            turbine.awaitComplete()
        }
    }

    @Test
    fun getWeatherFail() = runTest {
        val response =
            setUpResponse(HttpURLConnection.HTTP_BAD_REQUEST, "")
        mockWebServer.enqueue(response)
        turbineScope {
            val turbine =
                weatherRepository.getWeather(0f, 0f).testIn(backgroundScope)
            Assert.assertEquals(
                (turbine.awaitItem() as AppResult.Error).error.code, 400
            )
            turbine.awaitComplete()
        }
    }

    @Test
    fun getCitySuccess() = runTest {
        val response =
            setUpResponse(HttpURLConnection.HTTP_OK, MockDataSource.getMockCityEntityJson())
        mockWebServer.enqueue(response)
        turbineScope {
            val turbine =
                weatherRepository.searchCityByName("Ho Chi Minh").testIn(backgroundScope)
            Assert.assertEquals(
                (turbine.awaitItem() as AppResult.Success<List<City>>).data.firstOrNull()?.name,
                "Ho Chi Minh City"
            )
            turbine.awaitComplete()
        }
    }

    @Test
    fun getCityFail() = runTest {
        val response =
            setUpResponse(HttpURLConnection.HTTP_BAD_REQUEST, "")
        mockWebServer.enqueue(response)
        turbineScope {
            val turbine =
                weatherRepository.searchCityByName("Ho chi minh").testIn(backgroundScope)
            Assert.assertEquals(
                (turbine.awaitItem() as AppResult.Error).error.code, 400
            )
            turbine.awaitComplete()
        }
    }

    @Test
    fun getForecastSuccess() = runTest {
        val response =
            setUpResponse(HttpURLConnection.HTTP_OK, MockDataSource.getMockForecastEntityJson())
        mockWebServer.enqueue(response)
        turbineScope {
            val turbine =
                weatherRepository.getForecast(0f, 0f, 20).testIn(backgroundScope)
            Assert.assertEquals(
                (turbine.awaitItem() as AppResult.Success<Forecast>).data.city?.name,
                "Ho Chi Minh City"
            )
            turbine.awaitComplete()
        }
    }

    @Test
    fun getForecastFail() = runTest {
        val response =
            setUpResponse(HttpURLConnection.HTTP_BAD_REQUEST, "")
        mockWebServer.enqueue(response)
        turbineScope {
            val turbine =
                weatherRepository.getForecast(0f, 0f, 20).testIn(backgroundScope)
            Assert.assertEquals(
                (turbine.awaitItem() as AppResult.Error).error.code, 400
            )
            turbine.awaitComplete()
        }
    }


}