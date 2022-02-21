package com.achaka.intervals.training.di

import com.achaka.intervals.training.model.TrainingDao
import com.achaka.intervals.training.model.TrainingRepository
import com.achaka.intervals.training.model.TrainingRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class TrainingModule {

    @Provides
    fun provideTrainingRepository(trainingDao: TrainingDao): TrainingRepository = TrainingRepositoryImpl(trainingDao)

}