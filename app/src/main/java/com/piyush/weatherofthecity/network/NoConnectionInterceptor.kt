package com.piyush.weatherofthecity.network

import com.piyush.weatherofthecity.WeatherApp
import com.piyush.weatherofthecity.utils.extentions.isNetNotConnected
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoConnectionInterceptor @Inject constructor(
    private val context: WeatherApp
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (context.isNetNotConnected()) {
            throw NoInternetException()
        }else {
            chain.proceed(chain.request())
        }
    }
}