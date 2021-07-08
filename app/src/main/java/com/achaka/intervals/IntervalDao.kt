package com.achaka.intervals

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IntervalDao {
    @Query("SELECT * FROM intervals ORDER BY number ASC ")
    suspend fun getIntervals(trainingName: String): List<Interval>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInterval(interval: Interval)

    @Query("DELETE ")
    suspend fun deleteAllIntervals()

    @Query("DELETE FROM intervals")
    suspend fun deleteInterval(intervalNumber: Int)

}