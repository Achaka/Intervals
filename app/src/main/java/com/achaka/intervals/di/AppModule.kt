package com.achaka.intervals.di

import android.app.Application
import com.achaka.intervals.IntervalsApp
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: Application) {

    @Provides
    fun provideApplication(): Application = app
}