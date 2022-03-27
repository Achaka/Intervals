package com.achaka.intervals.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.intervals.interval.viewmodel.IntervalsViewModel
import com.achaka.intervals.training.viewmodel.TrainingsViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val provider = providers[modelClass]

        return provider?.get() as T ?: throw IllegalArgumentException("unsupported view model type: $modelClass")

    }
}