package com.achaka.intervals.interval.di

import com.achaka.intervals.di.AppComponent
import com.achaka.intervals.di.scope.FragmentScope
import com.achaka.intervals.di.viewmodel.ViewModelModule
import com.achaka.intervals.interval.ui.IntervalsFragment
import com.achaka.intervals.training.di.TrainingsModule
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [IntervalsModule::class, TrainingsModule::class, ViewModelModule::class])
interface IntervalsComponent {

    fun inject(intervalsFragment: IntervalsFragment)
}