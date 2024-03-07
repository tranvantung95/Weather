package com.example.wheather.domain.usecase

import app.cash.turbine.turbineScope
import com.example.core.domain.AppResult
import com.example.core.domain.ErrorType
import com.example.data.mockdata.MockDataSource
import com.example.data.mockdata.stringToObject
import com.example.unitest.BaseUnitTest
import com.example.wheather.domain.gateway.IWeatherGateway
import com.example.wheather.domain.model.Forecast
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class GetForecastUseCaseTest : BaseUnitTest() {
    private val iWeatherGateway: IWeatherGateway = mockk()
    private lateinit var getForecastUseCase: GetForecastUseCase

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun setUp() {
        super.setUp()
        getForecastUseCase = GetForecastUseCase(iWeatherGateway)
        val forecastJson = MockDataSource.getMockForecastBusinessModelJson()
        every {
            iWeatherGateway.getForecast(any(), any(), any())
        } answers {
            flowOf(AppResult.Success(stringToObject(forecastJson)))
        }
    }

    @Test
    fun getForecastSuccess() = runTest {
        turbineScope {
            val turbine = getForecastUseCase.invoke(GetForecastUseCaseParam(lat = 0f, lon = 0f, 22))
                .testIn(backgroundScope)
            val result = turbine.awaitItem() as? AppResult.Success<Forecast>
            Assert.assertEquals(22, result?.data?.forecast?.size)
            turbine.awaitComplete()
        }
    }

    @Test
    fun getForecastFail() = runTest {
        every {
            iWeatherGateway.getForecast(any(), any(), any())
        } answers {
            flowOf(AppResult.Error(com.example.core.domain.Error(403, "bad request", ErrorType.IO)))
        }
        turbineScope {
            val turbine = getForecastUseCase.invoke(GetForecastUseCaseParam(lat = 0f, lon = 0f, 22))
                .testIn(backgroundScope)
            val result = turbine.awaitItem() as? AppResult.Error
            Assert.assertEquals("bad request", result?.error?.message)
            turbine.awaitComplete()
        }
    }
}