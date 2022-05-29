package com.achaka.intervals.interval.model.domain_model

data class LadderTrainingItem(
    override val id: Int,
    var description: String,
    val isCompleted: Boolean,
    var weight: Float,
    var progress: Int
): ExerciseAdapterItem() {

    override val content: ExerciseAdapterItem
        get() = this

}