package com.achaka.intervals.di

import android.app.Application
import com.achaka.intervals.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: Application) {

    @Provides
    @ApplicationScope
    fun provideApplication(): Application = app
}