package com.achaka.intervals.di

import android.app.Application
import androidx.room.Room
import com.achaka.intervals.AppDatabase
import com.achaka.intervals.di.scope.ApplicationScope
import com.achaka.intervals.interval.model.IntervalDao
import com.achaka.intervals.training.model.TrainingDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @ApplicationScope
    fun provideDatabase(application: Application): AppDatabase = Room.databaseBuilder(application, AppDatabase::class.java, "intervals_db").build()

    @Provides
    fun provideTrainingDao(database: AppDatabase): TrainingDao = database.trainingDao()

    @Provides
    fun provideIntervalsDao(database: AppDatabase): IntervalDao = database.intervalDao()
}