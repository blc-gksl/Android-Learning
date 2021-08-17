package com.example.myweatherapp.data.repository

import android.location.Location
import androidx.lifecycle.LiveData
import com.example.myweatherapp.data.db.CurrentWeatherDao
import com.example.myweatherapp.data.db.WeatherLocationDao
import com.example.myweatherapp.data.db.entity.WeatherLocation
import com.example.myweatherapp.data.db.unitlocalized.SpecificCurrentWeatherEntry
import com.example.myweatherapp.data.network.WeatherNetworkDataSource
import com.example.myweatherapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*


import com.example.myweatherapp.data.provider.LocationProvider
import org.threeten.bp.LocalDate




class WeatherRepositoryImpl (

    private val currentWeatherDao : CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val weatherLocationDao: WeatherLocationDao,
    private val locationProvider: LocationProvider

    ): WeatherRepository {

    init{

        weatherNetworkDataSource.apply {
            downloadedTodayWeather.observeForever { newCurrentWeather ->
                persistFetchTodayWeather(newCurrentWeather)
            }

        }

    }

    override suspend fun getCurrentWeather(): LiveData<out SpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext  currentWeatherDao.getWeatherMetric()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchTodayWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch (Dispatchers.IO){
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.weatherLocation)
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchTodayWeather()

            return
        }


        if(isFetchTodayNeeded(ZonedDateTime.now().minusHours(1))){
            fetchTodayWeather()
        }

    }

    private suspend fun fetchTodayWeather() {
        weatherNetworkDataSource.fetchTodayWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }



    private fun isFetchTodayNeeded(lastFetchTime : ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}