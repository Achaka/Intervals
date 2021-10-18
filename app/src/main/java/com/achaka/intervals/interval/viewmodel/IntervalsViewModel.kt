package com.achaka.intervals.interval.viewmodel

import androidx.lifecycle.ViewModel
import com.achaka.intervals.interval.model.Interval
import com.achaka.intervals.interval.model.IntervalDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable


class IntervalsViewModel(private val intervalDao: IntervalDao,
                               ): ViewModel() {

    private val subscriptions = CompositeDisposable()

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

    fun getIntervals(trainingId: Long): Flowable<List<Interval>> {
        return intervalDao.getIntervals(trainingId)
    }

    fun insertInterval(interval: Interval): Completable {
        return intervalDao.insertInterval(interval)
    }

    fun insertIntervals(intervals: List<Interval>): Completable {
        return intervalDao.insertIntervals(intervals)
    }

    fun updateIntervals(intervals: List<Interval>): Completable {
        return intervalDao.updateIntervals(intervals)
    }

    fun deleteInterval(intervalId: Long): Completable {
        return intervalDao.deleteInterval(intervalId)
    }

    fun clear(trainingId: Long): Completable {
        return intervalDao.deleteIntervalsFromTraining(trainingId)
    }

}