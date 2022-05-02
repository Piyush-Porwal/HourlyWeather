package com.piyush.weatherofthecity.data.weather.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastData(
    val lat: Double? = 0.0,
    val lon: Double? = 0.0,
    val timezone: String? = null,
    val timezone_offset: Int? = 0,
    val hourly: List<HourlyForecastData>? = emptyList()
): Parcelable

@Parcelize
data class HourlyForecastData(
    val dt: Long? = 0,
    val temp: Double? = 0.0,
    val feels_like: Double? = 0.0,
    val pressure: Int? = 0,
    val humidity: Int? = 0,
    val dew_point: Double? = 0.0,
    val uvi: Double? = 0.0,
    val clouds: Int? = 0,
    val visibility: Int? = 0,
    val wind_speed: Double? = 0.0,
    val wind_deg: Int? = 0,
    val wind_gust: Double? = 0.0,
    val weather: List<Weather>? = emptyList(),
    val pop: Double? = 0.0
):Parcelable

@Parcelize
data class Weather(
    val id: Int? = 0,
    val main: String? = null,
    val description: String? = null,
):Parcelable


