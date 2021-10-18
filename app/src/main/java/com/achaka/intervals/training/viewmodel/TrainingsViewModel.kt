package com.achaka.intervals.training.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.intervals.training.model.Training
import com.achaka.intervals.training.model.TrainingDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class TrainingsViewModel(private val trainingDao: TrainingDao): ViewModel() {

    private val subscriptions = CompositeDisposable()
    private val trainingsList = mutableListOf<Training>()

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

    fun insertTraining(training: Training): Single<Long> {
        return trainingDao.insertTraining(training)
    }

    fun getTrainings(): Flowable<List<Training>> {
        return trainingDao.getTrainings()
    }

    fun deleteTraining(trainingId: Long): Completable {
        return trainingDao.deleteTraining(trainingId)
    }
}