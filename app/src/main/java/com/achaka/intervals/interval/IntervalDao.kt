package com.achaka.intervals.interval

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IntervalDao {
//    @Query("SELECT * FROM intervals ORDER BY number ASC ")
//    suspend fun getIntervals(trainingName: String): List<Interval>

    @Query("SELECT * FROM intervals WHERE training_id = :trainingId ORDER BY number ASC")
    fun getIntervals(trainingId: Long): Flow<List<Interval>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInterval(interval: Interval)

    @Query("DELETE FROM intervals WHERE training_id = :trainingId")
    suspend fun deleteIntervalsFromTraining(trainingId: Long)

    @Query("DELETE FROM intervals WHERE interval_id = :intervalId")
    suspend fun deleteInterval(intervalId: Int)

}