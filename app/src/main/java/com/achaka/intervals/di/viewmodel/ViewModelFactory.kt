package com.achaka.intervals.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.intervals.interval.viewmodel.IntervalsViewModel
import com.achaka.intervals.training.viewmodel.TrainingsViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val trainingsViewModelProvider: Provider<TrainingsViewModel>,
    private val intervalsViewModelProvider: Provider<IntervalsViewModel>
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            TrainingsViewModel::class.java -> trainingsViewModelProvider.get() as T
            IntervalsViewModel::class.java -> intervalsViewModelProvider.get() as T
            else -> throw IllegalArgumentException("unsupported view model type: $modelClass")
        }
    }
}