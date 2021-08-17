package com.example.myweatherapp.data.network

import androidx.lifecycle.LiveData
import com.example.myweatherapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedTodayWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchTodayWeather(
        location : String,
        languageCode : String
    )
}