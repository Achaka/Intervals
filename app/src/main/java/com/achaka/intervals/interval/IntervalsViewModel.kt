package com.achaka.intervals.interval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class IntervalsViewModel(private val intervalDao: IntervalDao,
                               ): ViewModel() {

    val scope = viewModelScope

    fun getTestIntervals(): MutableList<Interval> {
        val intervals = mutableListOf<Interval>()
        intervals.add(Interval(0L, 1, "first", 50, false, 0L, "6:00"))
        intervals.add(Interval(1L, 2, "second", 50, false, 0L, "5:23"))
        return intervals
    }

    fun insertInterval(interval: Interval) {
        scope.launch {
            intervalDao.insertInterval(interval)
        }
    }

    fun getIntervals(trainingId: Long): Flow<List<Interval>> {
        return intervalDao.getIntervals(trainingId)
    }

}