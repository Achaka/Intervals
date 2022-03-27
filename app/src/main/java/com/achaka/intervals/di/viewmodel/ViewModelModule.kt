package com.achaka.intervals.di.viewmodel

import androidx.lifecycle.ViewModel
import com.achaka.intervals.interval.viewmodel.IntervalsViewModel
import com.achaka.intervals.training.viewmodel.TrainingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TrainingsViewModel::class)
    abstract fun trainingsViewModel(trainingsViewModel: TrainingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IntervalsViewModel::class)
    abstract fun intervalsViewModel(intervalsViewModel: IntervalsViewModel): ViewModel
}