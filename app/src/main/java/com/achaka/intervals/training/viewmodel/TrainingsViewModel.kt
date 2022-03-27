package com.achaka.intervals.training.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.achaka.intervals.training.model.Training
import com.achaka.intervals.training.model.TrainingRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TrainingsViewModel @Inject constructor(private val trainingRepository: TrainingRepository): ViewModel() {

    private val subscriptions = CompositeDisposable()

    private val _trainingsList = MutableLiveData<Training>()
    val trainingsList: LiveData<Training> = _trainingsList

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