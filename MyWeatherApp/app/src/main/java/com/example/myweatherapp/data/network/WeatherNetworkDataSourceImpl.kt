package com.example.myweatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myweatherapp.data.network.response.CurrentWeatherResponse
import com.example.myweatherapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val weatherStackWeatherApiService : WeatherStackWeatherApiService
)    : WeatherNetworkDataSource{

    private val _downloadedTodayWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedTodayWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedTodayWeather

    override suspend fun fetchTodayWeather(location: String, languageCode: String) {
        try {
            val fetchedTodayWeather = weatherStackWeatherApiService
                .getCurrentWeather(location, languageCode)
                .await()
            _downloadedTodayWeather.postValue(fetchedTodayWeather)
        }
        catch( e:NoConnectivityException){
          Log.e("Connectivity", "No internet connection.", e)
    }
    }


}