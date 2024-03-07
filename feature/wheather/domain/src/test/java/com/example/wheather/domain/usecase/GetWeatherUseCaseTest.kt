package com.example.wheather.domain.usecase

import app.cash.turbine.turbineScope
import com.example.core.domain.AppResult
import com.example.core.domain.ErrorType
import com.example.data.mockdata.MockDataSource
import com.example.data.mockdata.stringToObject
import com.example.unitest.BaseUnitTest
import com.example.wheather.domain.gateway.IWeatherGateway
import com.example.wheather.domain.model.Weather
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class GetWeatherUseCaseTest : BaseUnitTest() {
    private val iWeatherGateway: IWeatherGateway = mockk()
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun setUp() {
        super.setUp()
        getWeatherUseCase = GetWeatherUseCase(iWeatherGateway)
        val weatherJson = MockDataSource.getMockWeatherBusinessModelJson()
        every {
            iWeatherGateway.getWeather(any(), any())
        } answers {
            flowOf(AppResult.Success(stringToObject(weatherJson)))
        }
    }

    @Test
    fun getWeatherSuccess() = runTest {
        turbineScope {
            val turbine = getWeatherUseCase.invoke(GetWeatherUseCaseParam(lat = 0f, lon = 0f))
                .testIn(backgroundScope)
            val result = turbine.awaitItem() as? AppResult.Success<Weather>
            Assert.assertEquals("Ho Chi Minh City", result?.data?.cityName)
            turbine.awaitComplete()
        }
    }

    @Test
    fun getWeatherFail() = runTest {
        every {
            iWeatherGateway.getWeather(any(), any(),)
        } answers {
            flowOf(AppResult.Error(com.example.core.domain.Error(403, "bad request", ErrorType.IO)))
        }
        turbineScope {
            val turbine = getWeatherUseCase.invoke(GetWeatherUseCaseParam(lat = 0f, lon = 0f))
                .testIn(backgroundScope)
            val result = turbine.awaitItem() as? AppResult.Error
            Assert.assertEquals("bad request", result?.error?.message)
            turbine.awaitComplete()
        }
    }
}