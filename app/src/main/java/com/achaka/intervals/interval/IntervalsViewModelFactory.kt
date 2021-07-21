package com.achaka.intervals.interval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class IntervalsViewModelFactory(private val intervalDao: IntervalDao)
    :ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(IntervalsViewModel::class.java)) {
            return IntervalsViewModel(intervalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}