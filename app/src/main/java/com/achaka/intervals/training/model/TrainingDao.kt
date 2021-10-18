package com.achaka.intervals.training.model

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TrainingDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertTraining(training: Training): Single<Long>

    @Query ("SELECT * FROM trainings")
    fun getTrainings(): Flowable<List<Training>>

    @Query ("DELETE FROM trainings WHERE training_id =:trainingId")
    fun deleteTraining(trainingId: Long): Completable

}