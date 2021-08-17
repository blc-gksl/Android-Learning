package com.example.myweatherapp.ui.weather.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.data.repository.WeatherRepository

class TodayWeatherViewModelFactory (
        private val weatherRepository: WeatherRepository
        ): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodayViewModel(weatherRepository) as T
    }

}