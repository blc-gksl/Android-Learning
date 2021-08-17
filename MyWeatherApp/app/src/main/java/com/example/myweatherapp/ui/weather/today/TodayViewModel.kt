package com.example.myweatherapp.ui.weather.today

import androidx.lifecycle.ViewModel
import com.example.myweatherapp.data.repository.WeatherRepository
import com.example.myweatherapp.internal.lazyDeferred
import com.example.myweatherapp.ui.base.WeatherViewModel

class TodayViewModel (
    private val weatherRepository: WeatherRepository
        ): WeatherViewModel(weatherRepository) {

        val weather by lazyDeferred {
            weatherRepository.getCurrentWeather()
        }
}
