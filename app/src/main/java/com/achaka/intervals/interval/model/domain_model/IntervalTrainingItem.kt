package com.achaka.intervals.interval.model.domain_model

data class IntervalTrainingItem(
    override val id: Int,
    var number: Int,
    var description: String,
    var seconds: Int,
    val isCompleted: Boolean,
    var weight: Float,
    var progress: Int
): ExerciseAdapterItem() {

    override val content: ExerciseAdapterItem
        get() = this
}