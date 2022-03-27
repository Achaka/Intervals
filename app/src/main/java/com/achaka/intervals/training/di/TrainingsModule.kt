package com.achaka.intervals.training.di

import com.achaka.intervals.training.model.TrainingDao
import com.achaka.intervals.training.model.TrainingRepository
import com.achaka.intervals.training.model.TrainingRepositoryImpl
import com.achaka.intervals.training.ui.TrainingsFragment
import dagger.Module
import dagger.Provides

@Module
class TrainingsModule {

    @Provides
    fun provideTrainingsFragment() = TrainingsFragment()

    @Provides
    fun provideTrainingsRepository(dao: TrainingDao): TrainingRepository = TrainingRepositoryImpl(dao)


}