package com.piyush.weatherofthecity.data.weather

import com.piyush.weatherofthecity.BuildConfig
import com.piyush.weatherofthecity.data.weather.model.ForecastData
import com.piyush.weatherofthecity.data.weather.model.GeoData
import com.piyush.weatherofthecity.utils.async.ThreadManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val threadManager: ThreadManager
) {

    /**
     * Get Weather Forecast List
     */
    suspend fun getWeatherForecastList(lat: Double, lon: Double): Pair<ForecastData?, Int> {
        val param = HashMap<String,String>()
        param[PARAM_LAT] = lat.toString()
        param[PARAM_LON] = lon.toString()
        param[PARAM_API_KEY] = BuildConfig.API_KEY

        return withContext(threadManager.io) {
            val response = weatherApi.getForecastList(param)
            response.body() to response.code()
        }
    }

    /**
     * Get Geo Location Data
     */
    suspend fun getGeoDataList(city: String): Pair<List<GeoData>?, Int> {
        val param = HashMap<String,String>()

        param[PARAM_CITY] = city
        param[PARAM_LIMIT] = "5"
        param[PARAM_API_KEY] = BuildConfig.API_KEY

        return withContext(threadManager.io) {
            val response = weatherApi.getGeoList(param)
            response.body() to response.code()
        }
    }

    companion object {
        private const val PARAM_LAT = "lat"
        private const val PARAM_LON = "lon"
        private const val PARAM_API_KEY = "appid"
        private const val PARAM_CITY = "q"
        private const val PARAM_LIMIT = "limit"
    }
}