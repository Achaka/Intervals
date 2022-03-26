package com.achaka.intervals.di

import com.achaka.intervals.di.scope.ApplicationScope
import com.achaka.intervals.interval.model.IntervalDao
import com.achaka.intervals.training.model.TrainingDao
import com.achaka.intervals.ui.MainActivity
import dagger.Component

@ApplicationScope
@Component(modules = [DatabaseModule::class, AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun trainingDao(): TrainingDao
    fun intervalDao(): IntervalDao
}