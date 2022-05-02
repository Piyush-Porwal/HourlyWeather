package com.piyush.weatherofthecity.di.bindings

import com.piyush.weatherofthecity.ui.forecastDetail.FragmentForecastDetail
import com.piyush.weatherofthecity.ui.forecastlist.FragmentForecastList
import com.piyush.weatherofthecity.ui.weatherlookup.FragmentWeatherLookup
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindings {

    @ContributesAndroidInjector
    abstract fun contributeFragmentForecastDetail(): FragmentForecastDetail

    @ContributesAndroidInjector
    abstract fun contributeFragmentForecastList(): FragmentForecastList

    @ContributesAndroidInjector
    abstract fun contributeFragmentWeatherLookup(): FragmentWeatherLookup
}
