package com.example.myweatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myweatherapp.data.db.entity.CurrentWeatherEntry
import com.example.myweatherapp.data.db.entity.WeatherLocation
import java.security.AccessControlContext

@Database(
    entities = [CurrentWeatherEntry::class,
                WeatherLocation::class],
    version = 1
)
@TypeConverters(Converters::class, LocalDateConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract  fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object{
        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()
        

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java, "weather.db")
                    .build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{ instance = it }
        }

    }

}