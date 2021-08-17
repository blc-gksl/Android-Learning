package com.example.myweatherapp.ui.weather.today

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.myweatherapp.R
import com.example.myweatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.today_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class TodayFragment : ScopedFragment(), KodeinAware {


    override val kodein: Kodein by closestKodein()
    private val viewModelFactory:TodayWeatherViewModelFactory by instance()


    private lateinit var viewModel: TodayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.today_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).
                    get(TodayViewModel::class.java)
        bindUI()

//        // Just to show while building at initial steps
//        val apiService = WeatherStackWeatherApiService(ConnectivityInterceptorImpl(this.requireContext()))
//        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
//
//        weatherNetworkDataSource.downloadedTodayWeather.observe(viewLifecycleOwner, Observer{
//                tv_today_fragment.text = it.toString()
//        })
//
//        GlobalScope.launch(Dispatchers.Main) {
//            weatherNetworkDataSource.fetchTodayWeather("London", "en")
//        }
//        //
    }

    private fun bindUI() = launch{
        val todayWeather = viewModel.weather.await()

        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        todayWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.weatherDescription)
            updatePrecipitation(it.precipitation)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            Glide.with(this@TodayFragment)
                .load("${it.weatherIconUrl}")
                .into(iv_condition_icon)


        })
    }


    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    @SuppressLint("SetTextI18n")
    private fun updateTemperatures(temperature: Int, feelsLike: Int) {
        val unitAbbreviation = "°C"
        tv_temperature.text = "$temperature$unitAbbreviation"
        tv_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: List<String>) {
        tv_condition.text = condition.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = "mm"
        tv_precipitation.text = "Preciptiation: $precipitationVolume $unitAbbreviation"
    }

    @SuppressLint("SetTextI18n")
    private fun updateWind(windDirection: String, windSpeed: Int) {
        val unitAbbreviation = "kph"
        tv_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    @SuppressLint("SetTextI18n")
    private fun updateVisibility(visibilityDistance: Int) {
        val unitAbbreviation = "km"
        tv_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

}