package com.example.wheather.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core.common.loadImageFromUrl
import com.example.core.presentation.ErrorUiModel
import com.example.core.presentation.getQueryTextChangeStateFlow
import com.example.wheather.presentation.databinding.WeatherFragmentBinding
import com.example.wheather.presentation.mapper.ForecastDataType
import com.example.wheather.presentation.model.FavoriteCityUiModel
import com.example.wheather.presentation.model.XAxisFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.google.android.material.chip.ChipGroup
import com.google.android.material.chip.ChipGroup.OnCheckedStateChangeListener
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment(), OnCheckedStateChangeListener {
    private var weatherFragmentBinding: WeatherFragmentBinding? = null
    private val viewModel: WeatherViewModel by viewModel()
    private val loadingView: CircularProgressIndicator? by lazy {
        weatherFragmentBinding?.loadingView
    }
    private val weatherImage: AppCompatImageView? by lazy {
        weatherFragmentBinding?.ivWeatherIcon
    }
    private val edtCity: AppCompatEditText? by lazy {
        weatherFragmentBinding?.edtCity
    }
    private val tvDescription: AppCompatTextView? by lazy {
        weatherFragmentBinding?.tvDescription
    }
    private val tvCurrentTemperature: AppCompatTextView? by lazy {
        weatherFragmentBinding?.tvCurrentTemperature
    }
    private val tvHumidity: AppCompatTextView? by lazy {
        weatherFragmentBinding?.tvHumidity
    }
    private val tvWind: AppCompatTextView? by lazy {
        weatherFragmentBinding?.tvWind
    }
    private val chart: LineChart? by lazy {
        weatherFragmentBinding?.lineChart
    }
    private val chipGroup: ChipGroup? by lazy {
        weatherFragmentBinding?.chipGroup
    }
    private val tvCityName: AppCompatTextView? by lazy {
        weatherFragmentBinding?.tvCityName
    }
    private val ivFavorite: AppCompatImageView? by lazy {
        weatherFragmentBinding?.ivFavorite
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherFragmentBinding = WeatherFragmentBinding.inflate(inflater, container, false)
        return weatherFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataObserve()
        loadData()
        initEvent()
    }

    private fun showCityPopupWindow(dataSource: List<FavoriteCityUiModel>) {
        edtCity?.let {
            val cityPopupWindow =
                CityPopupWindow(context = requireContext(), it, dataSource) { city ->
                    city?.let {
                        viewModel.getWeather(city.lat, city.lon)
                    }

                }

            cityPopupWindow.showPopup()
        }
    }

    private fun initEvent() {
        chipGroup?.setOnCheckedStateChangeListener(this)
        chipGroup?.check(R.id.temperatureChip)
        lifecycleScope.launch {
            edtCity?.getQueryTextChangeStateFlow()
                ?.debounce(1000)
                ?.filter { query ->
                    return@filter query.isNotEmpty()
                }
                ?.distinctUntilChanged()
                ?.flowOn(Dispatchers.Default)
                ?.collect { searchQuery ->
                    viewModel.getCityByName(searchQuery)
                }
        }
        ivFavorite?.setOnClickListener {
            viewModel.saveCityFavorite()
            Toast.makeText(requireContext(), "Save Success", Toast.LENGTH_SHORT).show()
        }
        edtCity?.setOnFocusChangeListener { view, b ->
            if (b) {
                viewModel.getFavoriteCity()
            }
        }
    }

    private fun showErrorMessage(error: ErrorUiModel) {
        val alertDialog =
            AlertDialog.Builder(requireContext()).setMessage(error.message).setTitle(error.title)
                .create()
        alertDialog.show()
    }

    private fun showLoading() {
        loadingView?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView?.visibility = View.GONE
    }

    private fun dataObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.cityState.collect {
                        when (it) {
                            is CityState.Success -> {
                                hideLoading()
                                if (it.data.isEmpty()) {
                                    return@collect
                                }
                                viewModel.getWeather(
                                    lat = it.data.firstOrNull()?.lat ?: 0f,
                                    lon = it.data.firstOrNull()?.lon ?: 0f
                                )
                            }

                            is CityState.Error -> {
                                hideLoading()
                                showErrorMessage(it.error)
                            }

                            is CityState.Loading -> {
                                showLoading()
                            }
                        }

                    }
                }
                launch {
                    viewModel.weather.collect {
                        when (it) {
                            is WeatherState.Loading -> {
                                showLoading()
                            }

                            is WeatherState.Error -> {
                                hideLoading()
                                showErrorMessage(it.error)
                                setUpViewWithoutData()
                            }

                            is WeatherState.Success -> {
                                hideLoading()
                                setupViewHasData(it)
                            }
                        }
                    }
                }
                launch {
                    viewModel.cityFavoriteList.collect {
                        if (it.isEmpty()) {
                            return@collect
                        }
                        showCityPopupWindow(it)
                    }
                }
            }
        }
    }

    private fun setUpViewWithoutData() {
        weatherFragmentBinding?.llContent?.visibility = View.GONE
    }

    private fun setupViewHasData(weather: WeatherState.Success) {
        weatherFragmentBinding?.llContent?.visibility = View.VISIBLE
        tvCityName?.text = weather.weathers?.cityName
        weatherImage?.loadImageFromUrl(weather.weathers.iconUrl.orEmpty())
        tvCurrentTemperature?.text = getString(
            R.string.temperature_format,
            weather.weathers.temp.toString()
        )
        tvDescription?.text = weather.weathers.main
        tvHumidity?.text = getString(
            R.string.humidity_format,
            weather.weathers.humidity.toString()
        )
        tvWind?.text =
            getString(R.string.wind_format, weather.weathers.wind.toString())
        setupLineChart(
            weather.forecast?.temperatureLineData,
            weather.forecast.forecastDetail?.firstOrNull()?.timeStamp ?: 0L
        )
    }

    private fun loadData() {
        //viewModel.getCityByName("Ho chi minh")
    }

    private fun setupLineChart(lineData: LineData?, firstDataPoint: Long) {
        val xAxis = chart?.xAxis
        chart?.axisRight?.isEnabled = false
        xAxis?.valueFormatter = XAxisFormatter(referenceValue = firstDataPoint.toInt(), "dd/MM")
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        chart?.data = lineData
        chart?.invalidate()
    }

    override fun onCheckedChanged(group: ChipGroup, checkedIds: MutableList<Int>) {
        val pairData = when (checkedIds.firstOrNull()) {
            R.id.humidityChip -> {
                viewModel.getLineChartByTye(ForecastDataType.Humidity)
            }

            R.id.temperatureChip -> {
                viewModel.getLineChartByTye(ForecastDataType.Temperature)
            }

            R.id.windChip -> {
                viewModel.getLineChartByTye(ForecastDataType.Wind)
            }

            else -> null
        }
        if (pairData?.first != null) {
            setupLineChart(pairData.first, pairData.second)
        }
    }
}