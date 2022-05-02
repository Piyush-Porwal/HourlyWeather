package com.piyush.weatherofthecity

import android.app.Application
import com.piyush.weatherofthecity.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class WeatherApp : Application(), HasAndroidInjector {

    /**
     * Injector for Android components
     */
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().app(this).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}