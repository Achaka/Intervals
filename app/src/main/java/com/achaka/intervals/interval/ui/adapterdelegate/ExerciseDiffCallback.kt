package com.achaka.intervals.interval.ui.adapterdelegate

import androidx.recyclerview.widget.DiffUtil
import com.achaka.intervals.interval.model.domain_model.IntervalPacedTrainingItem
import com.achaka.intervals.interval.model.domain_model.RegularTrainingItem

class ExerciseDiffCallback: DiffUtil.ItemCallback<ExerciseItem>() {

    override fun areItemsTheSame(oldItem: ExerciseItem, newItem: ExerciseItem): Boolean {
        return (oldItem::class.java == newItem::class.java && oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: ExerciseItem, newItem: ExerciseItem): Boolean {
        if (oldItem::class.java == newItem::class.java) {
            return when {
                oldItem is IntervalPacedTrainingItem -> {
                    val old = oldItem as? IntervalPacedTrainingItem
                    val new = newItem as? IntervalPacedTrainingItem
                    old == new
                }
                oldItem is RegularTrainingItem -> {
                    val old = oldItem as? RegularTrainingItem
                    val new = newItem as? RegularTrainingItem
                    old == new
                }
                else -> false
            }
        } else return false
    }
}