package com.example.myweatherapp.data.repository

import android.location.Location
import androidx.lifecycle.LiveData
import com.example.myweatherapp.data.db.entity.WeatherLocation
import com.example.myweatherapp.data.db.unitlocalized.SpecificCurrentWeatherEntry

interface WeatherRepository {

    suspend fun getCurrentWeather(): LiveData<out SpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}