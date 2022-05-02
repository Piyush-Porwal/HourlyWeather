package com.piyush.weatherofthecity.data.weather

import com.piyush.weatherofthecity.data.weather.model.ForecastData
import com.piyush.weatherofthecity.data.weather.model.GeoData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    /**
     * Get Weather forecast list by hourly
     */
    @GET("/data/2.5/onecall")
    @Throws(Exception::class)
    suspend fun getForecastList(@QueryMap payload: Map<String, String>): Response<ForecastData>

    /**
     * Get Geo Location list
     */
    @GET("/geo/1.0/direct")
    @Throws(Exception::class)
    suspend fun getGeoList(@QueryMap payload: Map<String, String>): Response<List<GeoData>>

    companion object {
        /**
         * Factory function for [WeatherApi]
         */
        fun create(retroFit: Retrofit): WeatherApi = retroFit.create(
            WeatherApi::class.java
        )
    }
}