package com.example.myweatherapp.data.db.unitlocalized

interface SpecificCurrentWeatherEntry {
    val temperature: Int
    val weatherDescription: List<String>
    val weatherIconUrl : List<String>
    val windSpeed : Int
    val windDirection : String
    val precipitation : Double
    val feelsLikeTemperature : Int
    val visibilityDistance : Int

}