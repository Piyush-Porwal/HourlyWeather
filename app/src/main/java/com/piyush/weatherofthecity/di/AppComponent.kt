package com.piyush.weatherofthecity.di

import android.app.Application
import com.piyush.weatherofthecity.WeatherApp
import com.piyush.weatherofthecity.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppBindings::class
    ]
)
interface AppComponent : AndroidInjector<WeatherApp> {
    fun inject(app: Application)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppComponent
    }

}