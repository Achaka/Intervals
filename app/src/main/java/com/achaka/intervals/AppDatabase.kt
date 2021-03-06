package com.achaka.intervals

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.achaka.intervals.interval.model.Interval
import com.achaka.intervals.interval.model.IntervalDao
import com.achaka.intervals.training.model.Training
import com.achaka.intervals.training.model.TrainingDao

@Database(entities = [Training::class, Interval::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun intervalDao(): IntervalDao
    abstract fun trainingDao(): TrainingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "intervals_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

