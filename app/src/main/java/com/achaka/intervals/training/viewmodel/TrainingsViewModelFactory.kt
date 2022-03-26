package com.achaka.intervals.training.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.intervals.training.model.TrainingDao
import com.achaka.intervals.training.model.TrainingRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

class TrainingsViewModelFactory @Inject constructor(private val trainingRepositoryProvider: Provider<TrainingRepository>): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrainingsViewModel::class.java)) {
            return TrainingsViewModel(trainingRepositoryProvider.get()) as T
        }
        else throw IllegalArgumentException()
    }
}