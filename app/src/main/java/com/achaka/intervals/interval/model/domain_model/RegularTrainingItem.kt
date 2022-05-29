package com.achaka.intervals.interval.model.domain_model

import com.achaka.intervals.interval.ui.adapterdelegate.ExerciseItem

data class RegularTrainingItem(
    override val id: Int,
    var number: Int,
    var description: String,
    val isCompleted: Boolean,
    var weight: Float,
    var reps: Int,
    var progress: Int
): ExerciseItem {

}