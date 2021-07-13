package com.achaka.intervals

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {

    @Insert
    suspend fun insertTraining(training: Training)

    @Transaction
    @Query ("SELECT * FROM trainings")
    fun getTrainingWithIntervals(): List<TrainingWithIntervals>


    @Query ("SELECT * FROM trainings")
    fun getTrainings(): Flow<List<Training>>

    @Query("SELECT * FROM trainings WHERE training_id = :trainingId")
    suspend fun getTraining(trainingId: Long): Training
}