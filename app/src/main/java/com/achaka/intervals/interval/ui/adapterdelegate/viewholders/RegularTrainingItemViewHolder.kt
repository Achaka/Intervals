package com.achaka.intervals.interval.ui.adapterdelegate.viewholders

import android.text.SpannableStringBuilder
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.RegularTrainingItemBinding
import com.achaka.intervals.interval.model.IntervalFragmentMode
import com.achaka.intervals.interval.model.domain_model.RegularTrainingItem
import com.achaka.intervals.interval.ui.adapterdelegate.ExerciseItem

class RegularTrainingItemViewHolder(
    val binding: RegularTrainingItemBinding,
    val mode: IntervalFragmentMode
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(exerciseItem: ExerciseItem) {
        val regularItem = exerciseItem as? RegularTrainingItem
        when(mode) {
            IntervalFragmentMode.EDIT_MODE, IntervalFragmentMode.NEW_TRAINING_EDIT_MODE -> {
                binding.apply {
                    delete.visibility = View.VISIBLE
                    completeIndicator.visibility = View.GONE
                    description.isEnabled = true
                    description.doAfterTextChanged {
                        regularItem?.description = it.toString()
                    }
                    reps.isEnabled = true
                    weight.isEnabled = true
                }
            }
            else -> {
                binding.apply {
                    delete.visibility = View.GONE
                    completeIndicator.visibility = View.VISIBLE
                    description.isEnabled = false
                    reps.isEnabled = false
                    weight.isEnabled = false
                }
            }
        }
        binding.apply {
            description.text = SpannableStringBuilder.valueOf(regularItem?.description)
            reps.text = SpannableStringBuilder.valueOf(regularItem?.reps.toString())
            weight.text = SpannableStringBuilder.valueOf(regularItem?.weight.toString())
        }
        binding.delete.setOnClickListener {
//            deleteClickListener.deleteInterval(interval?.id)
        }
    }
}