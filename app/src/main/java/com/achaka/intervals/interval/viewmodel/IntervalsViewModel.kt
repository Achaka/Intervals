package com.achaka.intervals.interval.viewmodel

import androidx.lifecycle.ViewModel
import com.achaka.intervals.interval.model.Interval
import com.achaka.intervals.interval.model.IntervalsRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class IntervalsViewModel @Inject constructor (private val intervalsRepository: IntervalsRepository): ViewModel() {

    private val subscriptions = CompositeDisposable()

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

    fun getIntervals(trainingId: Long): Flowable<List<Interval>> {
        return intervalsRepository.getIntervals(trainingId)
    }

    fun insertInterval(interval: Interval): Completable {
        return intervalsRepository.insertInterval(interval)
    }

    fun insertIntervals(intervals: List<Interval>): Completable {
        return intervalsRepository.insertIntervals(intervals)
    }

    fun updateIntervals(intervals: List<Interval>): Completable {
        return intervalsRepository.updateIntervals(intervals)
    }

    fun deleteInterval(intervalId: Long): Completable {
        return intervalsRepository.deleteInterval(intervalId)
    }

    fun clear(trainingId: Long): Completable {
        return intervalsRepository.deleteIntervalsFromTraining(trainingId)
    }

}