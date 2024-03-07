package com.example.wheather.presentation

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import com.example.androidtest.BaseUiTest
import com.example.core.domain.AppResult
import com.example.core.domain.ErrorType
import com.example.data.mockdata.MockDataSource
import com.example.data.mockdata.stringToObject
import com.example.feature.localstorage.domain.GetCityFavoriteUseCase
import com.example.feature.localstorage.domain.SaveCityFavoriteUseCase
import com.example.wheather.domain.usecase.GetCityUseCase
import com.example.wheather.domain.usecase.GetForecastUseCase
import com.example.wheather.domain.usecase.GetWeatherUseCase
import com.example.wheather.presentation.mapper.CityCachingModelMapper
import com.example.wheather.presentation.mapper.CityUiMapper
import com.example.wheather.presentation.mapper.FavoriteCityMapper
import com.example.wheather.presentation.mapper.ForecastMapper
import com.example.wheather.presentation.mapper.ForecastToLineDataMapper
import com.example.wheather.presentation.mapper.WeatherMapper
import com.example.wheather.presentation.screen.WeatherScreen
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.core.presentation.R as presentationR

class WeatherFragmentTest : BaseUiTest() {
    var fragmentScenario: FragmentScenario<WeatherFragment>? = null
    private val getWeatherUseCase: GetWeatherUseCase = mockk()
    private val getCityUseCase: GetCityUseCase = mockk()
    private val cityUiMapper: CityUiMapper = CityUiMapper()
    private val weatherMapper: WeatherMapper = WeatherMapper()
    private val forecastToLineDataMapper: ForecastToLineDataMapper = ForecastToLineDataMapper()
    private val forecastMapper: ForecastMapper =
        ForecastMapper(weatherMapper, forecastToLineDataMapper)
    private val forecastUseCase: GetForecastUseCase = mockk()
    private val saveCityToFavoriteUseCase: SaveCityFavoriteUseCase = mockk()
    private val getCityFavoriteUseCase: GetCityFavoriteUseCase = mockk()
    private val favoriteCityMapper: FavoriteCityMapper = FavoriteCityMapper()
    private val cityCachingModelMapper: CityCachingModelMapper = CityCachingModelMapper()
    private val viewModel: WeatherViewModel = spyk(
        WeatherViewModel(
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
        )
    )
    private var mockModule: Module = module(createdAtStart = true, override = true) {
        viewModel {
            viewModel
        }
    }

    @Before
    fun setUp() {
        loadKoinModules(mockModule)
    }

    @Test
    fun testScreenWithoutData() {
        fragmentScenario =
            launchFragmentInContainer(themeResId = presentationR.style.Theme_Wheather)
        run {
            step("check root screen is gone") {
                WeatherScreen.llRoot {
                    isGone()
                }
            }
            step("check search city is show") {
                WeatherScreen {
                    edtCity {
                        isVisible()
                    }
                }
            }
        }
    }

    private fun setUpDataCaseSuccess() {
        every {
            getCityUseCase.invoke(any())
        } answers {
            val cityJson = MockDataSource.getMockCityBusinessModelJson()
            flowOf(AppResult.Success(stringToObject(cityJson)))
        }
        every {
            getWeatherUseCase.invoke(any())
        } answers {
            val weatherJson = MockDataSource.getMockWeatherBusinessModelJson()
            flowOf(AppResult.Success(stringToObject(weatherJson)))
        }

        every {
            getCityFavoriteUseCase.invoke()
        } answers {
            listOf()
        }
        every {
            forecastUseCase.invoke(any())
        } answers {
            val forecastJson = MockDataSource.getMockForecastBusinessModelJson()
            flowOf(AppResult.Success(stringToObject(forecastJson)))
        }
        every {
            saveCityToFavoriteUseCase.invoke(any())
        } just Runs
    }

    @Test
    fun testScreenHaveData() {
        setUpDataCaseSuccess()
        fragmentScenario =
            launchFragmentInContainer(themeResId = presentationR.style.Theme_Wheather)
        run {
            step("type text search city") {
                WeatherScreen {
                    edtCity {
                        typeText("Ho Chi Minh")
                        Espresso.closeSoftKeyboard()
                    }
                }
            }
            step("check root screen is show") {
                WeatherScreen {
                    llRoot {
                        isVisible()
                    }
                }
            }
            step("check weather value") {
                WeatherScreen {
                    tvTemperature {
                        containsText("31.95")
                    }
                    tvHumidity {
                        containsText("66")
                    }
                    tvWind {
                        containsText("2.06")
                    }
                    tvCityName {
                        hasText("Ho Chi Minh City")
                    }
                }
            }
            step("click humidity Chip") {
                WeatherScreen {
                    chipGroup {
                        selectChip(R.id.humidityChip)
                    }
                }
            }
            step("click wind Chip") {
                WeatherScreen {
                    chipGroup {
                        selectChip(R.id.windChip)
                    }
                }
            }

        }
    }

    @Test
    fun testCaseCallApiFail() {
        every {
            getCityFavoriteUseCase.invoke()
        } answers {
            listOf()
        }
        every {
            getCityUseCase.invoke(any())
        } answers {
            flowOf(
                AppResult.Error(
                    com.example.core.domain.Error(
                        code = 1,
                        "Something wrong",
                        ErrorType.NETWORK
                    )
                )
            )
        }
        fragmentScenario =
            launchFragmentInContainer(themeResId = presentationR.style.Theme_Wheather)
        run {
            step("type text search") {
                WeatherScreen {
                    edtCity {
                        typeText("Ho Chi Minh")
                        Espresso.closeSoftKeyboard()
                    }
                }
                WeatherScreen {
                    dialog {
                        isVisible()
                        message.hasText("Something wrong")
                    }
                }
            }
        }
    }

    @Test
    fun testCaseSaveCityToFavorite() {
        setUpDataCaseSuccess()
        every {
            getCityFavoriteUseCase.invoke()
        } answers {
            val favoriteCityJson = MockDataSource.getMockFavoriteCityBusinessModelJson()
            stringToObject(favoriteCityJson)
        }
        fragmentScenario =
            launchFragmentInContainer(themeResId = presentationR.style.Theme_Wheather)
        run {
            step("search city") {
                WeatherScreen {
                    edtCity {
                        typeText("Ho Chi Minh")
                        Espresso.closeSoftKeyboard()
                    }
                }
            }
        }
    }


    @After
    fun clear() {
        unmockkAll()
    }
}