package com.example.myweatherapp.data.db.entity


import com.google.gson.annotations.SerializedName
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime



const val WEATHER_LOCATION_ID = 0


@Entity(tableName = "weather_location")
data class WeatherLocation(
    val name: String,
    val country: String,
    val region: String,
    val lat: String,
    val lon: String,
    @SerializedName("timezone_id")
    val timezoneİd: String,
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    @SerializedName("utc_offset")
    val utcOffset: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID

    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(localtimeEpoch.toLong())
            val zoneId = ZoneId.of(timezoneİd)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }
}