package com.piyush.weatherofthecity.data.weather.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeoData(
    val name: String? = null,
    val lat: Double? = 0.0,
    val lon: Double? = 0.0,
    val country: String? = null,
    val state: String? = null
): Parcelable
