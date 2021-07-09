package com.achaka.intervals

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TrainingDao {
    @Transaction
    @Query ("SELECT * FROM trainings")
    fun getTrainingWithIntervals(): List<TrainingWithIntervals>

    suspend fun getTraining(): Training
}