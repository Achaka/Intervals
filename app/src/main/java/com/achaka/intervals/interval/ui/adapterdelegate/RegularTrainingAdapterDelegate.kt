package com.achaka.intervals.interval.ui.adapterdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.IntervalPacedItemBinding
import com.achaka.intervals.databinding.RegularTrainingItemBinding
import com.achaka.intervals.interval.model.IntervalFragmentMode
import com.achaka.intervals.interval.model.domain_model.IntervalPacedTrainingItem
import com.achaka.intervals.interval.model.domain_model.RegularTrainingItem
import com.achaka.intervals.interval.ui.adapterdelegate.viewholders.IntervalPacedItemViewHolder
import com.achaka.intervals.interval.ui.adapterdelegate.viewholders.RegularTrainingItemViewHolder

class RegularTrainingAdapterDelegate(@LayoutRes val layoutResId: Int) {

    private var mode: IntervalFragmentMode = IntervalFragmentMode.EDIT_MODE

    fun isForViewType(items: List<ExerciseItem>, position: Int) =
        items[position] is RegularTrainingItem

    fun onCreateViewHolder(
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding = RegularTrainingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegularTrainingItemViewHolder(binding, mode)
    }

    fun onBindViewHolder(items: List<ExerciseItem>, holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as? IntervalPacedItemViewHolder).setId(getItem(position).id)
        (holder as? RegularTrainingItemViewHolder)?.bind(items[position])
    }
}