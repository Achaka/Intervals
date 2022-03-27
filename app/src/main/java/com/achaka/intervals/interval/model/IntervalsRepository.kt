package com.achaka.intervals.interval.model

import io.reactivex.Completable
import io.reactivex.Flowable

interface IntervalsRepository {

    fun getIntervals(trainingId: Long): Flowable<List<Interval>>

    fun insertInterval(interval: Interval): Completable

    fun insertIntervals(intervals: List<Interval>): Completable

    fun updateIntervals(intervals: List<Interval>): Completable

    fun deleteInterval(intervalId: Long): Completable

    fun deleteIntervalsFromTraining(trainingId: Long): Completable
}