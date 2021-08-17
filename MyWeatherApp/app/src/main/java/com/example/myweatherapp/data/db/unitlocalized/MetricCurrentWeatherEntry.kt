package com.example.myweatherapp.data.db.unitlocalized

import androidx.room.ColumnInfo

class MetricCurrentWeatherEntry(
    @ColumnInfo(name = "temperature")
    override val temperature: Int,
    @ColumnInfo(name = "weatherDescriptions")
    override val weatherDescription: List<String>,
    @ColumnInfo(name = "weatherİcons")
    override val weatherIconUrl: List<String>,
    @ColumnInfo(name = "windSpeed")
    override val windSpeed: Int,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "precip")
    override val precipitation: Double,
    @ColumnInfo(name = "feelslike")
    override val feelsLikeTemperature: Int,
    @ColumnInfo(name = "visibility")
    override val visibilityDistance: Int



):SpecificCurrentWeatherEntry