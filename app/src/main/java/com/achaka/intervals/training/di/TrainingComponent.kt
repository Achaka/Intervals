package com.achaka.intervals.training.di

import com.achaka.intervals.di.AppComponent
import com.achaka.intervals.di.scope.FragmentScope
import com.achaka.intervals.training.ui.TrainingsFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [TrainingsModule::class])
interface TrainingComponent {

    fun inject(trainingsFragment: TrainingsFragment)
}