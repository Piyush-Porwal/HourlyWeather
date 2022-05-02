package com.piyush.weatherofthecity.di

import com.piyush.weatherofthecity.MainActivity
import com.piyush.weatherofthecity.di.bindings.FragmentBindings
import com.piyush.weatherofthecity.di.bindings.ViewModelBindings
import com.piyush.weatherofthecity.utils.async.ThreadManager
import com.piyush.weatherofthecity.utils.async.ThreadManagerImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {

    @ContributesAndroidInjector(
        modules = [
            FragmentBindings::class,
            ViewModelBindings::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    abstract fun provideThreadManager(threadManager: ThreadManagerImpl): ThreadManager
}