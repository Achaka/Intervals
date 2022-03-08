package com.achaka.intervals.di

import com.achaka.intervals.di.scope.ApplicationScope
import com.achaka.intervals.training.di.TrainingsModule
import com.achaka.intervals.training.model.TrainingDao
import com.achaka.intervals.training.model.TrainingDao_Impl
import com.achaka.intervals.training.ui.TrainingsFragment
import com.achaka.intervals.ui.MainActivity
import dagger.Component

@ApplicationScope
@Component(modules = [DatabaseModule::class, AppModule::class, TrainingsModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun trainingDao(): TrainingDao
}