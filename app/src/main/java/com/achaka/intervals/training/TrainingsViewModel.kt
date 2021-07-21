package com.achaka.intervals.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TrainingsViewModel(private val trainingDao: TrainingDao): ViewModel() {

    val scope = viewModelScope

    fun insertTraining(training: Training) {
        scope.launch {
            trainingDao.insertTraining(training)
        }
    }

    fun getTestTrainings(): MutableList<Training> {
        val trainings = mutableListOf<Training>()
        trainings.add(Training(0L, "firstT"))
        trainings.add(Training(1L, "secondT"))
        return trainings
    }

    fun getTrainings(): Flow<List<Training>> =
        trainingDao.getTrainings()

}