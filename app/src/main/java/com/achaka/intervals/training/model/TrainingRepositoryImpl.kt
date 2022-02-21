package com.achaka.intervals.training.model

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(val trainingDao: TrainingDao): TrainingRepository {

    override fun insertTraining(training: Training): Single<Long> {
        return trainingDao.insertTraining(training)
    }

    override fun getTrainings(): Flowable<List<Training>> {
        return trainingDao.getTrainings()
    }

    override fun deleteTraining(trainingId: Long): Completable {
        return trainingDao.deleteTraining(trainingId)
    }

}