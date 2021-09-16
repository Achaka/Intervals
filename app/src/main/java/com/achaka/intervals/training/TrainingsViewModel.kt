package com.achaka.intervals.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TrainingsViewModel(private val trainingDao: TrainingDao): ViewModel() {

    private val subscriptions = CompositeDisposable()
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

//    fun getTrainings(): Flow<List<Training>> =
//        trainingDao.getTrainings()
//
    fun getTrainings(): Observable<List<Training>> {
        return trainingDao.getTrainings()
    }
}