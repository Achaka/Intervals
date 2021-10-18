package com.achaka.intervals.training.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.intervals.training.model.TrainingDao
import java.lang.IllegalArgumentException

class TrainingsViewModelFactory(private val trainingDao: TrainingDao): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrainingsViewModel::class.java)) {
            return TrainingsViewModel(trainingDao) as T
        }
        else throw IllegalArgumentException()
    }
}