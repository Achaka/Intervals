package com.achaka.intervals.interval.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.intervals.interval.model.IntervalDao
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