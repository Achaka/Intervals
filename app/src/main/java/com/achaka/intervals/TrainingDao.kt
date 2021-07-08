package com.achaka.intervals

import androidx.room.Query
import androidx.room.Transaction

interface TrainingDao {

    @Transaction
    @Query ("SELECT * FROM trainings")
    fun getTrainingWithIntervals(): List<TrainingWithIntervals>

}