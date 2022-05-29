package com.achaka.intervals.interval.model.domain_model

data class RoundTrainingItem(
    override val id: Int,
    var number: Int,
    var description: String,
    val isCompleted: Boolean,
    var weight: Float,
    var reps: Int,
    var progress: Int
): ExerciseAdapterItem() {

    override val content: ExerciseAdapterItem
        get() = this
}