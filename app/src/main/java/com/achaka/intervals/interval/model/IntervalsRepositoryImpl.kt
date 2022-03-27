package com.achaka.intervals.interval.model

import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class IntervalsRepositoryImpl @Inject constructor(private val intervalsDao: IntervalDao): IntervalsRepository {

    override fun getIntervals(trainingId: Long): Flowable<List<Interval>> {
        return intervalsDao.getIntervals(trainingId)
    }

    override fun insertInterval(interval: Interval): Completable {
        return intervalsDao.insertInterval(interval)
    }

    override fun insertIntervals(intervals: List<Interval>): Completable {
        return intervalsDao.insertIntervals(intervals)
    }

    override fun updateIntervals(intervals: List<Interval>): Completable {
        return intervalsDao.updateIntervals(intervals)
    }

    override fun deleteInterval(intervalId: Long): Completable {
        return intervalsDao.deleteInterval(intervalId)
    }

    override fun deleteIntervalsFromTraining(trainingId: Long): Completable {
        return intervalsDao.deleteIntervalsFromTraining(trainingId)
    }
}