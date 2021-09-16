package com.achaka.intervals.interval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class IntervalsViewModel(private val intervalDao: IntervalDao,
                               ): ViewModel() {


    private val subscriptions = CompositeDisposable()
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

    fun insertIntervals(intervals: List<Interval>) {
        intervals.forEach {
            scope.launch {
                intervalDao.insertInterval(it)
            }
        }
    }

    fun getIntervals(trainingId: Long): Flow<List<Interval>> {
        return intervalDao.getIntervals(trainingId)
    }

    fun updateIntervals(intervals: List<Interval>) {
        scope.launch {
            intervalDao.updateIntervals(intervals)
        }
    }

    fun updateInterval(interval: Interval) {
        scope.launch { intervalDao.updateInterval(interval) }
    }


//    fun updateDescIntervalsById(intervalId: Int, newDesc: String) {
//        scope.launch { intervalDao.updateDescIntervalsById(intervalId, newDesc) }
//    }
//
//    fun updateTimeToGoById(intervalId: Int, newTimeToGo: String) {
//        scope.launch { intervalDao.updateDescIntervalsById(intervalId, newTimeToGo) }
//    }
//
//    fun updateSuggestedPaceById(intervalId: Int, newSuggestedPace: String) {
//        scope.launch { intervalDao.updateDescIntervalsById(intervalId, newSuggestedPace) }
//    }
//
//    fun updateNumberById(intervalId: Int, newNumber: String) {
//        scope.launch { intervalDao.updateDescIntervalsById(intervalId, newNumber) }
//    }
//
//    fun updateById() {
//
//    }

    fun clear(trainingId: Long) {
        scope.launch {
            intervalDao.deleteIntervalsFromTraining(trainingId)
        }
    }

}