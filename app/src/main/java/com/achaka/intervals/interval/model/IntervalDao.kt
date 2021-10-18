package com.achaka.intervals.interval.model

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface IntervalDao {

    @Query("SELECT * FROM intervals WHERE training_id = :trainingId ORDER BY number ASC")
    fun getIntervals(trainingId: Long): Flowable<List<Interval>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertInterval(interval: Interval): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIntervals(intervals: List<Interval>): Completable

    @Update
    fun updateInterval(interval: Interval): Completable

    @Update
    fun updateIntervals(interval: List<Interval>): Completable

    @Query("DELETE FROM intervals WHERE interval_id = :intervalId")
    fun deleteInterval(intervalId: Long): Completable

    @Query("DELETE FROM intervals WHERE training_id = :trainingId")
    fun deleteIntervalsFromTraining(trainingId: Long): Completable

}