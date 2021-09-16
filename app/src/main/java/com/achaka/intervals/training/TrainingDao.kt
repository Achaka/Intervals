package com.achaka.intervals.training

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.achaka.intervals.TrainingWithIntervals
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TrainingDao {

    @Insert
    suspend fun insertTraining(training: Training)

    @Transaction
    @Query ("SELECT * FROM trainings")
    fun getTrainingWithIntervals(): List<TrainingWithIntervals>


//    @Query ("SELECT * FROM trainings")
//    fun getTrainings(): Flow<List<Training>>

    @Query ("SELECT * FROM trainings")
    fun getTrainings(): Observable<List<Training>>

    @Query("SELECT * FROM trainings WHERE training_id = :trainingId")
    suspend fun getTraining(trainingId: Long): Training
}