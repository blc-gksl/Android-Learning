package com.example.myweatherapp.ui.base

import com.example.myweatherapp.data.repository.WeatherRepository
import androidx.lifecycle.ViewModel;
import com.example.myweatherapp.internal.lazyDeferred

abstract class WeatherViewModel (
    private val weatherRepository: WeatherRepository
    ) : ViewModel() {


        val weatherLocation by lazyDeferred {
            weatherRepository.getWeatherLocation()
        }
}