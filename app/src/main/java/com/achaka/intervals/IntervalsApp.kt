package com.achaka.intervals

import android.app.Application
import com.achaka.intervals.di.AppComponent
import com.achaka.intervals.di.AppModule
import com.achaka.intervals.di.DaggerAppComponent
import com.achaka.intervals.di.DatabaseModule


class IntervalsApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .build()
        super.onCreate()
    }
}