package com.achaka.intervals.training.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.intervals.training.model.Training
import com.achaka.intervals.training.model.TrainingDao
import com.achaka.intervals.training.model.TrainingRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class TrainingsViewModel(private val trainingRepository: TrainingRepository): ViewModel() {

    private val subscriptions = CompositeDisposable()
    private val trainingsList = mutableListOf<Training>()

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

    fun insertTraining(training: Training): Single<Long> {
        return trainingRepository.insertTraining(training)
    }

    fun getTrainings(): Flowable<List<Training>> {
        return trainingRepository.getTrainings()
    }

    fun deleteTraining(trainingId: Long): Completable {
        return trainingRepository.deleteTraining(trainingId)
    }
}