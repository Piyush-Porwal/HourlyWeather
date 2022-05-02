package com.piyush.weatherofthecity.di.bindings

import androidx.lifecycle.ViewModel
import com.piyush.weatherofthecity.di.modules.viewmodel.ViewModelKey
import com.piyush.weatherofthecity.ui.forecastlist.ForecastListViewModel
import com.piyush.weatherofthecity.ui.weatherlookup.WeatherLookupViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBindings {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherLookupViewModel::class)
    abstract fun bindWeatherLookupViewModel(viewModel: WeatherLookupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForecastListViewModel::class)
    abstract fun bindForecastListViewModel(viewModel: ForecastListViewModel): ViewModel
}
