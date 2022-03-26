package com.achaka.intervals

import android.app.Application
import com.achaka.intervals.di.AppComponent
import com.achaka.intervals.di.AppModule
import com.achaka.intervals.di.DaggerAppComponent
import com.achaka.intervals.di.DatabaseModule
import com.achaka.intervals.interval.di.DaggerIntervalsComponent
import com.achaka.intervals.interval.di.IntervalsComponent
import com.achaka.intervals.interval.di.IntervalsModule
import com.achaka.intervals.training.di.DaggerTrainingComponent
import com.achaka.intervals.training.di.TrainingComponent
import com.achaka.intervals.training.di.TrainingsModule


class IntervalsApp: Application() {

    lateinit var appComponent: AppComponent
    lateinit var trainingComponent: TrainingComponent
    lateinit var intervalsComponent: IntervalsComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .build()

        trainingComponent = DaggerTrainingComponent.builder()
            .appComponent(appComponent)
            .trainingsModule(TrainingsModule())
            .build()

        intervalsComponent = DaggerIntervalsComponent.builder()
            .appComponent(appComponent)
            .intervalsModule(IntervalsModule())
            .build()

        super.onCreate()
    }
}