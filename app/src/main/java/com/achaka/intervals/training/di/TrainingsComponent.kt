package com.achaka.intervals.training.di

import com.achaka.intervals.di.AppComponent
import com.achaka.intervals.di.scope.FragmentScope
import com.achaka.intervals.di.viewmodel.ViewModelModule
import com.achaka.intervals.interval.di.IntervalsModule
import com.achaka.intervals.training.ui.TrainingsFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [TrainingsModule::class, IntervalsModule::class, ViewModelModule::class]
)
interface TrainingComponent {

    fun inject(trainingsFragment: TrainingsFragment)
}