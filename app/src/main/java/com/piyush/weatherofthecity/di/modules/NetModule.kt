package com.piyush.weatherofthecity.di.modules

import com.piyush.weatherofthecity.BuildConfig
import com.piyush.weatherofthecity.data.weather.WeatherApi
import com.piyush.weatherofthecity.network.NoConnectionInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Dagger module for network ops
 */
@Module
class NetModule {

    @Module
    companion object {

        /**
         * Provide logging interceptor
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

        /**
         * Provide OkHttp
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideOkHttp(
            loggingInterceptor: HttpLoggingInterceptor,
            noConnectionInterceptor: NoConnectionInterceptor
        ): OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(noConnectionInterceptor)
                .build()

        /**
         * Provide Retrofit
         */
        @Singleton
        @JvmStatic
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder()
                            .add(KotlinJsonAdapterFactory())
                            .build()
                    )
                )
                .client(okHttpClient)
                .build()

        /**
         * Create [WeatherApi]
         */
        @JvmStatic
        @Provides
        @Singleton
        fun provideWeatherApi(retrofit: Retrofit): WeatherApi = WeatherApi.create(retrofit)
    }
}
