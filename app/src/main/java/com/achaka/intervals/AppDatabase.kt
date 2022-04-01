package com.achaka.intervals

import androidx.room.Database
import androidx.room.RoomDatabase
import com.achaka.intervals.interval.model.Interval
import com.achaka.intervals.interval.model.IntervalDao
import com.achaka.intervals.training.model.Training
import com.achaka.intervals.training.model.TrainingDao

@Database(entities = [Training::class, Interval::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun intervalDao(): IntervalDao
    abstract fun trainingDao(): TrainingDao
}

