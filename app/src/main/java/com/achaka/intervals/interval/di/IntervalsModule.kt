package com.achaka.intervals.interval.di

import androidx.room.Dao
import com.achaka.intervals.interval.model.IntervalDao
import com.achaka.intervals.interval.model.IntervalsRepository
import com.achaka.intervals.interval.model.IntervalsRepositoryImpl
import com.achaka.intervals.interval.ui.IntervalsFragment
import dagger.Module
import dagger.Provides

@Module
class IntervalsModule {

    @Provides
    fun provideIntervalsFragment() = IntervalsFragment()

    @Provides
    fun provideIntervalsRepository(intervalDao: IntervalDao): IntervalsRepository = IntervalsRepositoryImpl(intervalDao)
}