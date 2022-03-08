package com.achaka.intervals.di

import android.app.Application
import com.achaka.intervals.AppDatabase
import com.achaka.intervals.training.di.TrainingsModule
import com.achaka.intervals.training.model.Training
import com.achaka.intervals.training.model.TrainingDao
import com.achaka.intervals.training.model.TrainingRepository
import com.achaka.intervals.training.ui.TrainingsFragment
import com.achaka.intervals.ui.MainActivity
import dagger.Component

@ApplicationScope
@Component(modules = [DatabaseModule::class, AppModule::class, TrainingsModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(application: Application)
    fun inject(database: AppDatabase)
    fun inject(dao: TrainingDao)
    fun inject(repository: TrainingRepository)
    fun inject(trainingsFragment: TrainingsFragment)

}