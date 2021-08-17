package com.example.myweatherapp

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.example.myweatherapp.data.db.WeatherDatabase
import com.example.myweatherapp.data.network.*
import com.example.myweatherapp.data.repository.WeatherRepository
import com.example.myweatherapp.data.repository.WeatherRepositoryImpl
import com.example.myweatherapp.data.provider.LocationProvider
import com.example.myweatherapp.data.provider.LocationProviderImpl
import com.example.myweatherapp.ui.weather.today.TodayWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class WeatherApplication : Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }

        bind() from singleton { instance<WeatherDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherStackWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<WeatherRepository>() with singleton {WeatherRepositoryImpl(instance(), instance(), instance(), instance())}
        bind() from provider { TodayWeatherViewModelFactory(instance()) }


    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}