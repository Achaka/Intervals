package com.achaka.intervals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class IntervalsSharedViewModelFactory(private val intervalDao: IntervalDao,
                                      private val trainingDao: TrainingDao)
    :ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(IntervalsSharedViewModel::class.java)) {
            return IntervalsSharedViewModel(intervalDao, trainingDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}