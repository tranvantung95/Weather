package com.example.wheather.presentation

import app.cash.turbine.test
import com.example.core.domain.AppResult
import com.example.core.domain.ErrorEntity
import com.example.core.domain.ErrorType
import com.example.data.mockdata.MockDataSource
import com.example.data.mockdata.stringToObject
import com.example.feature.localstorage.domain.GetCityFavoriteUseCase
import com.example.feature.localstorage.domain.SaveCityFavoriteUseCase
import com.example.unitest.BaseUnitTest
import com.example.wheather.domain.usecase.GetCityUseCase
import com.example.wheather.domain.usecase.GetForecastUseCase
import com.example.wheather.domain.usecase.GetWeatherUseCase
import com.example.wheather.presentation.mapper.CityCachingModelMapper
import com.example.wheather.presentation.mapper.CityUiMapper
import com.example.wheather.presentation.mapper.FavoriteCityMapper
import com.example.wheather.presentation.mapper.ForecastDataType
import com.example.wheather.presentation.mapper.ForecastMapper
import com.example.wheather.presentation.mapper.ForecastToLineDataMapper
import com.example.wheather.presentation.mapper.WeatherMapper
import com.github.mikephil.charting.data.LineData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class WeatherViewModelTest : BaseUnitTest() {
    companion object {
        const val API_ERROR_MESSAGE = "Something wrong"
    }

    val errorEntity: ErrorEntity by lazy {
        ErrorEntity(code = 403, message = API_ERROR_MESSAGE, ErrorType.NETWORK)
    }

    private val getWeatherUseCase: GetWeatherUseCase = mockk()
    private val getCityUseCase: GetCityUseCase = mockk()
    private val cityUiMapper: CityUiMapper = CityUiMapper()
    private val weatherMapper: WeatherMapper = WeatherMapper()
    private val forecastToLineDataMapper: ForecastToLineDataMapper = mockk()
    private val forecastMapper: ForecastMapper =
        ForecastMapper(weatherMapper, forecastToLineDataMapper)
    private val forecastUseCase: GetForecastUseCase = mockk()
    private val saveCityToFavoriteUseCase: SaveCityFavoriteUseCase = mockk()
    private val getCityFavoriteUseCase: GetCityFavoriteUseCase = mockk()
    private val favoriteCityMapper: FavoriteCityMapper = FavoriteCityMapper()
    private val cityCachingModelMapper: CityCachingModelMapper = CityCachingModelMapper()
    lateinit var weatherViewModel: WeatherViewModel
    override fun setUp() {
        super.setUp()
        weatherViewModel = WeatherViewModel(
            getWeatherUseCase,
            getCityUseCase,
            cityUiMapper,
            weatherMapper,
            forecastMapper,
            forecastUseCase,
            saveCityToFavoriteUseCase,
            getCityFavoriteUseCase,
            favoriteCityMapper,
            cityCachingModelMapper
        ).apply {
            dispatcher = mainCoroutineRule.testDispatcher
        }
        every {
            forecastToLineDataMapper.mapToLineData(any(), ForecastDataType.Temperature)
        } answers {
            LineData()
        }
        every {
            forecastToLineDataMapper.mapToLineData(any(), ForecastDataType.Wind)
        } answers {
            LineData()
        }
        every {
            forecastToLineDataMapper.mapToLineData(any(), ForecastDataType.Humidity)
        } answers {
            LineData()
        }
    }

    @Test
    fun testGetCityByNameSuccess() = runTest {
        every {
            getCityUseCase.invoke(any())
        } answers {
            val cityJson = MockDataSource.getMockCityBusinessModelJson()
            flowOf(AppResult.Success(stringToObject(cityJson)))
        }
        launch {
            weatherViewModel.getCityByName("Ho Chi Minh")
        }
        weatherViewModel.cityState.test {
            Assert.assertEquals(CityState.Loading, awaitItem())
            Assert.assertEquals(1, (awaitItem() as CityState.Success).data.size)
        }
    }

    @Test
    fun testGetCityByNameFail() = runTest {
        every {
            getCityUseCase.invoke(any())
        } answers {
            flowOf(
                AppResult.Error(
                    com.example.core.domain.Error(
                        1,
                        API_ERROR_MESSAGE,
                        errorType = ErrorType.NETWORK
                    )
                )
            )
        }
        launch {
            weatherViewModel.getCityByName("Ho Chi Minh")
        }
        weatherViewModel.cityState.test {
            Assert.assertEquals(CityState.Loading, awaitItem())
            Assert.assertEquals(API_ERROR_MESSAGE, (awaitItem() as CityState.Error).error.message)
        }
    }

    @Test
    fun getWeatherSuccess() = runTest {
        initMockWeather()
        launch {
            weatherViewModel.getWeather(0f, 0f)
        }
        weatherViewModel.weather.test {
            Assert.assertEquals(WeatherState.Loading, awaitItem())
            val result = awaitItem() as WeatherState.Success
            Assert.assertEquals(result.weathers.main, "Clouds")
            Assert.assertEquals(result.forecast.forecastDetail?.size, 22)
        }
    }

    @Test
    fun getWeatherFailCaseWeatherApiError() = runTest {
        every {
            getWeatherUseCase.invoke(any())
        } answers {
            flowOf(AppResult.Error(errorEntity))
        }
        every {
            forecastUseCase.invoke(any())
        } answers {
            val forecastJson = MockDataSource.getMockForecastBusinessModelJson()
            flowOf(AppResult.Success(stringToObject(forecastJson)))
        }
        launch {
            weatherViewModel.getWeather(0f, 0f)
        }
        weatherViewModel.weather.test {
            Assert.assertEquals(WeatherState.Loading, awaitItem())
            Assert.assertEquals(
                (awaitItem() as WeatherState.Error).error.message,
                API_ERROR_MESSAGE
            )
        }
    }
    @Test
    fun getWeatherFailCaseForeCastApiError() = runTest {
        every {
            getWeatherUseCase.invoke(any())
        } answers {
            val weatherJson = MockDataSource.getMockWeatherBusinessModelJson()
            flowOf(AppResult.Success(stringToObject(weatherJson)))
        }
        every {
            forecastUseCase.invoke(any())
        } answers {
            flowOf(AppResult.Error(errorEntity))
        }
        launch {
            weatherViewModel.getWeather(0f, 0f)
        }
        weatherViewModel.weather.test {
            Assert.assertEquals(WeatherState.Loading, awaitItem())
            Assert.assertEquals(
                (awaitItem() as WeatherState.Error).error.message,
                API_ERROR_MESSAGE
            )
        }
    }

    private fun initMockWeather() {
        every {
            getWeatherUseCase.invoke(any())
        } answers {
            val weatherJson = MockDataSource.getMockWeatherBusinessModelJson()
            flowOf(AppResult.Success(stringToObject(weatherJson)))
        }
        every {
            forecastUseCase.invoke(any())
        } answers {
            val forecastJson = MockDataSource.getMockForecastBusinessModelJson()
            flowOf(AppResult.Success(stringToObject(forecastJson)))
        }
    }

    @Test
    fun getLineChartByTypeCaseTemperatureSuccess() = runTest {
        initMockWeather()
        launch {
            getWeatherSuccess()
            val result = weatherViewModel.getLineChartByTye(ForecastDataType.Temperature)
            Assert.assertNotNull(result)
        }
    }
    @Test
    fun getLineChartByTypeCaseWindSuccess() = runTest {
        initMockWeather()
        launch {
            getWeatherSuccess()
            val result = weatherViewModel.getLineChartByTye(ForecastDataType.Wind)
            Assert.assertNotNull(result)
        }
    }
    @Test
    fun getLineChartByTypeCaseHumiditySuccess() = runTest {
        initMockWeather()
        launch {
            getWeatherSuccess()
            val result = weatherViewModel.getLineChartByTye(ForecastDataType.Humidity)
            Assert.assertNotNull(result)
        }
    }
    @Test
    fun getLineChartByTypeFail() = runTest {
        initMockWeather()
        launch {
            getWeatherFailCaseWeatherApiError()
            val result = weatherViewModel.getLineChartByTye(ForecastDataType.Humidity)
            Assert.assertNull(result)
        }
    }
}