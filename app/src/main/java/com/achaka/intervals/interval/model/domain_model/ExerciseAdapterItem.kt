package com.achaka.intervals.interval.model.domain_model

abstract class ExerciseAdapterItem {

    abstract val id: Int
    abstract val content: ExerciseAdapterItem

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + content.hashCode()
        return result
    }
}