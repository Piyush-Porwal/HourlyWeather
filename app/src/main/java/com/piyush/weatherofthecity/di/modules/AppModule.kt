package com.piyush.weatherofthecity.di.modules

import android.app.Application
import com.piyush.weatherofthecity.WeatherApp
import com.piyush.weatherofthecity.di.modules.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Module(
    includes = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        NetModule::class,
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): WeatherApp = application as WeatherApp
}
