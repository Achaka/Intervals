package com.achaka.intervals.di

import android.content.Context
import com.achaka.intervals.IntervalsApp
import com.achaka.intervals.ui.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [])
interface ApplicationComponent
{
    fun inject(activity: MainActivity)
    fun inject(application: IntervalsApp)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}