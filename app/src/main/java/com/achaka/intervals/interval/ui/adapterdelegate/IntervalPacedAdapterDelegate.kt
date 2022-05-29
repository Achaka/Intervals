package com.achaka.intervals.interval.ui.adapterdelegate

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.IntervalPacedItemBinding
import com.achaka.intervals.interval.model.IntervalFragmentMode
import com.achaka.intervals.interval.model.domain_model.IntervalPacedTrainingItem
import com.achaka.intervals.interval.ui.adapterdelegate.viewholders.IntervalPacedItemViewHolder
import kotlin.properties.Delegates
import kotlin.reflect.KProperty


//create abstract class
class IntervalPacedAdapterDelegate(@LayoutRes val layoutResId: Int, val onDeleteClick: (Long) -> Unit) {

    var mode: IntervalFragmentMode by Delegates.observable(IntervalFragmentMode.EDIT_MODE) {
            _: KProperty<*>, _: IntervalFragmentMode, _: IntervalFragmentMode ->
    }

    fun isForViewType(items: List<ExerciseItem>, position: Int): Boolean {
        Log.d("TAG", (items[position] is IntervalPacedTrainingItem).toString())
        return items[position] is IntervalPacedTrainingItem
    }

    fun onCreateViewHolder(
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding = IntervalPacedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntervalPacedItemViewHolder(binding, onDeleteClick)
    }

    fun onBindViewHolder(items: List<ExerciseItem>, holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as? IntervalPacedItemViewHolder).setId(getItem(position).id)
        val viewHolder = holder as? IntervalPacedItemViewHolder
        viewHolder?.mode = mode
        viewHolder?.bind(items[position])
        Log.d("DELEGATE MODE", mode.name)
    }
}