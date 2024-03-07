package com.example.wheather.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.ErrorUiModel
import com.example.feature.localstorage.domain.GetCityFavoriteUseCase
import com.example.feature.localstorage.domain.SaveCityFavoriteUseCase
import com.example.wheather.domain.usecase.GetCityUseCase
import com.example.wheather.domain.usecase.GetCityUseCaseParam
import com.example.wheather.domain.usecase.GetForecastUseCase
import com.example.wheather.domain.usecase.GetForecastUseCaseParam
import com.example.wheather.domain.usecase.GetWeatherUseCase
import com.example.wheather.domain.usecase.GetWeatherUseCaseParam
import com.example.wheather.presentation.mapper.CityCachingModelMapper
import com.example.wheather.presentation.mapper.CityUiMapper
import com.example.wheather.presentation.mapper.FavoriteCityMapper
import com.example.wheather.presentation.mapper.ForecastDataType
import com.example.wheather.presentation.mapper.ForecastMapper
import com.example.wheather.presentation.mapper.WeatherMapper
import com.example.wheather.presentation.model.FavoriteCityUiModel
import com.github.mikephil.charting.data.LineData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getCityUseCase: GetCityUseCase,
    private val cityUiMapper: CityUiMapper,
    private val weatherMapper: WeatherMapper,
    private val forecastMapper: ForecastMapper,
    private val forecastUseCase: GetForecastUseCase,
    private val saveCityToFavoriteUseCase: SaveCityFavoriteUseCase,
    private val getCityFavoriteUseCase: GetCityFavoriteUseCase,
    private val favoriteCityMapper: FavoriteCityMapper,
    private val cityCachingModelMapper: CityCachingModelMapper
) : BaseViewModel() {
    private val _weather = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weather: StateFlow<WeatherState> = _weather.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = WeatherState.Loading
    )
    private val _cityFavoriteList = MutableStateFlow<List<FavoriteCityUiModel>>(listOf())
    val cityFavoriteList: StateFlow<List<FavoriteCityUiModel>> = _cityFavoriteList.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = listOf()
    )
    private val _cityState = MutableStateFlow<CityState>(CityState.Loading)
    val cityState: StateFlow<CityState> = _cityState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = CityState.Loading
    )

    fun saveCityFavorite() {
        val saveCity = (cityState.value as? CityState.Success)?.data?.firstOrNull()
        if (saveCity != null) {
            saveCityToFavoriteUseCase.invoke(favoriteCityMapper.mapCityModelToBusinessModel(saveCity))
        }
    }

    fun getFavoriteCity() {
        val result = getCityFavoriteUseCase.invoke()
        _cityFavoriteList.value = result.map {
            cityCachingModelMapper.mapToUiLayerModel(it)
        }.distinctBy {
            it.name
        }
    }

    fun getCityByName(cityName: String) {
        viewModelScope.launch(dispatcher ?: Dispatchers.Default) {
            _cityState.value = CityState.Loading
            getCityUseCase.invoke(GetCityUseCaseParam(name = cityName)).map { result ->
                result.safeCall()
            }.catch {
                _cityState.value = CityState.Error(it as ErrorUiModel)
            }.collect { result ->
                _cityState.value = CityState.Success(data = result.map {
                    cityUiMapper.mapToUiLayerModel(it)
                })
            }
        }
    }

    fun getWeather(lat: Float, lon: Float) {
        viewModelScope.launch(dispatcher ?: Dispatchers.Default) {
            combine(
                getWeatherUseCase.invoke(GetWeatherUseCaseParam(lat = lat, lon = lon)),
                forecastUseCase.invoke(
                    GetForecastUseCaseParam(lat = lat, lon = lon, numberOfSample = 22)
                )
            ) { weatherResponse, forecastResponse ->
                val weather = weatherResponse.safeCall()
                val forecast = forecastResponse.safeCall()
                _weather.value = WeatherState.Success(
                    weathers = weatherMapper.mapToUiLayerModel(weather),
                    forecast = forecastMapper.mapToUiLayerModel(forecast)
                )
            }.catch {
                _weather.value = WeatherState.Error(it as ErrorUiModel)
            }.collect {

            }
        }
    }

    fun getLineChartByTye(forecastType: ForecastDataType): Pair<LineData?, Long>? {
        return if (weather.value is WeatherState.Success) {
            val firstPoint =
                (weather.value as WeatherState.Success).forecast.forecastDetail?.firstOrNull()?.timeStamp
                    ?: 0L
            when (forecastType) {
                ForecastDataType.Temperature -> {
                    Pair(
                        first = (weather.value as WeatherState.Success).forecast.temperatureLineData,
                        second = firstPoint
                    )
                }

                ForecastDataType.Humidity -> {
                    Pair(
                        first = (weather.value as WeatherState.Success).forecast.humidityLineData,
                        second = firstPoint
                    )

                }

                ForecastDataType.Wind -> {
                    Pair(
                        first = (weather.value as WeatherState.Success).forecast.windLineData,
                        second = firstPoint
                    )

                }
            }

        } else {
            null
        }
    }
}

