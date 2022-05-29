package com.achaka.intervals.interval.model.domain_model

import com.achaka.intervals.interval.ui.adapterdelegate.ExerciseItem

data class IntervalPacedTrainingItem (
    override val id: Int,
    var number: Int,
    var description: String,
    var seconds: Int,
    val isCompleted: Boolean,
    override var trainingId: Long,
    var suggestedPace: String,
    var progress: Int
    ): ExerciseItem {

}