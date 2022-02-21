package com.achaka.intervals.training.model

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface TrainingRepository {

    fun insertTraining(training: Training): Single<Long>

    fun getTrainings(): Flowable<List<Training>>

    fun deleteTraining(trainingId: Long): Completable
}