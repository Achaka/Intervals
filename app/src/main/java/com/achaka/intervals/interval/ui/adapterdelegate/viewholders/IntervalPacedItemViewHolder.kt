package com.achaka.intervals.interval.ui.adapterdelegate.viewholders

import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.achaka.intervals.databinding.IntervalPacedItemBinding
import com.achaka.intervals.interval.model.IntervalFragmentMode
import com.achaka.intervals.interval.model.domain_model.ExerciseAdapterItem
import com.achaka.intervals.interval.model.domain_model.IntervalPacedTrainingItem
import com.achaka.intervals.interval.ui.IntervalsAdapter
import com.achaka.intervals.interval.ui.adapterdelegate.ExerciseItem
import java.lang.NumberFormatException
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class IntervalPacedItemViewHolder(
    val binding: IntervalPacedItemBinding,
    val onDeleteClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    var mode: IntervalFragmentMode by Delegates.observable(IntervalFragmentMode.EDIT_MODE) {
            _: KProperty<*>, _: IntervalFragmentMode, _: IntervalFragmentMode ->
    }

    fun bind(exerciseAdapterItem: ExerciseItem) {
        val interval = exerciseAdapterItem as? IntervalPacedTrainingItem
        Log.d("VH MODE", mode.name)
        when (mode) {
            IntervalFragmentMode.EDIT_MODE, IntervalFragmentMode.NEW_TRAINING_EDIT_MODE -> {
                binding.delete.visibility = View.VISIBLE
                binding.intervalDesc.isEnabled = true
                binding.intervalDesc.doAfterTextChanged {
                    interval?.description = it.toString()
                }
                binding.suggestedPace.isEnabled = true
                binding.suggestedPace.doAfterTextChanged { interval?.suggestedPace = it.toString() }
                binding.timeToGo.isEnabled = true
                binding.timeToGo.doAfterTextChanged {
                    val fieldValue = it.toString()
                    try {
                        interval?.seconds = fieldValue.toInt()
                    } catch (e: NumberFormatException) {

                    }
                }
                binding.completeIndicator.visibility = View.GONE
            }
            else -> {
                binding.delete.visibility = View.GONE
                binding.completeIndicator.visibility = View.VISIBLE
                binding.intervalDesc.isEnabled = false
                binding.suggestedPace.isEnabled = false
                binding.timeToGo.isEnabled = false
            }
        }
        binding.number.text = interval?.number.toString()
        binding.intervalDesc.text = SpannableStringBuilder.valueOf(interval?.description)
        binding.timeToGo.text?.clear()
        binding.timeToGo.text?.append(interval?.seconds.toString())
        binding.linearProgressIndicator.progress = interval?.progress ?: 0
        binding.suggestedPace.text?.clear()
        binding.suggestedPace.text?.append(interval?.suggestedPace)
        binding.delete.setOnClickListener {
            interval?.let { onDeleteClick(it.id.toLong()) }
        }
    }
}