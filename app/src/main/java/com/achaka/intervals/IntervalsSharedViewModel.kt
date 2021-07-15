package com.achaka.intervals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class IntervalsSharedViewModel(private val intervalDao: IntervalDao,
                               private val trainingDao: TrainingDao): ViewModel() {

    val scope = viewModelScope

    fun getTestIntervals(): MutableList<Interval>? {
        val intervals = mutableListOf<Interval>()
        intervals.add(Interval(0L, 1, "first", 50, false, 0L, "6:00"))
        intervals.add(Interval(1L, 2, "second", 50, false, 0L, "5:23"))
        return intervals
    }

    fun getTestTrainings(): MutableList<Training> {
        val trainings = mutableListOf<Training>()
        trainings.add(Training(0L, "firstT"))
        trainings.add(Training(1L, "secondT"))
        return trainings
    }

    fun getTrainings(): Flow<List<Training>> {

    }

    suspend fun insertTraining(training: Training) {
        scope.launch {
            trainingDao.insertTraining(training)
        }
    }
}