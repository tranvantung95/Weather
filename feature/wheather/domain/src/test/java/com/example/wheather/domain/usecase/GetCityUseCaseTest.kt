package com.example.wheather.domain.usecase

import app.cash.turbine.turbineScope
import com.example.core.domain.AppResult
import com.example.core.domain.ErrorType
import com.example.data.mockdata.MockDataSource
import com.example.data.mockdata.stringToObject
import com.example.unitest.BaseUnitTest
import com.example.wheather.domain.gateway.IWeatherGateway
import com.example.wheather.domain.model.City
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetCityUseCaseTest : BaseUnitTest() {
    private lateinit var getCityUseCase: GetCityUseCase
    private val iWeatherGateway: IWeatherGateway = mockk()

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun setUp() {
        super.setUp()
        getCityUseCase = GetCityUseCase(iWeatherGateway)
        val cityModel = MockDataSource.getMockCityBusinessModelJson()
        every {
            iWeatherGateway.searchCityByName(any())
        } answers {
            flowOf(AppResult.Success(stringToObject(cityModel)))
        }
    }

    @Test
    fun getCitySuccess() = runTest {
        turbineScope {
            val turbine = getCityUseCase.invoke(GetCityUseCaseParam("")).testIn(backgroundScope)
            val result = turbine.awaitItem() as? AppResult.Success<List<City>>
            Assert.assertEquals("Ho Chi Minh City", result?.data?.firstOrNull()?.name)
            turbine.awaitComplete()
        }
    }

    @Test
    fun getCityFail() = runTest() {
        every {
            iWeatherGateway.searchCityByName(any())
        } answers {
            flowOf(AppResult.Error(com.example.core.domain.Error(403, "bad request", ErrorType.IO)))
        }
        turbineScope {
            val turbine = getCityUseCase.invoke(GetCityUseCaseParam("")).testIn(backgroundScope)
            val result = turbine.awaitItem() as? AppResult.Error
            Assert.assertEquals("bad request", result?.error?.message)
            turbine.awaitComplete()
        }
    }
}