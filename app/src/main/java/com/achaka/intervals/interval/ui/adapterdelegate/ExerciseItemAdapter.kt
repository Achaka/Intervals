package com.achaka.intervals.interval.ui.adapterdelegate

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.R
import com.achaka.intervals.interval.model.IntervalFragmentMode
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

//преписать на ListAdapter
class ExerciseItemAdapter(val onDeleteClick: (Long) -> Unit): ListAdapter<ExerciseItem, RecyclerView.ViewHolder>(ExerciseDiffCallback())
{

    var items: List<ExerciseItem> = mutableListOf()

    var mode: IntervalFragmentMode by Delegates.observable(IntervalFragmentMode.EDIT_MODE) {
            _: KProperty<*>, _: IntervalFragmentMode, new: IntervalFragmentMode ->
    }

    val intervalPacedAdapterDelegate = IntervalPacedAdapterDelegate(R.layout.interval_paced_item, onDeleteClick)
    val regularTrainingAdapterDelegate = RegularTrainingAdapterDelegate(R.layout.regular_training_item)

    override fun getItemViewType(position: Int): Int {
        return when {
            intervalPacedAdapterDelegate.isForViewType(items, position) -> R.layout.interval_paced_item
            regularTrainingAdapterDelegate.isForViewType(items, position) -> R.layout.regular_training_item
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            intervalPacedAdapterDelegate.layoutResId -> intervalPacedAdapterDelegate.onCreateViewHolder(parent)
            regularTrainingAdapterDelegate.layoutResId -> regularTrainingAdapterDelegate.onCreateViewHolder(parent)
            else -> throw DelegateAdapterNotFoundException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            intervalPacedAdapterDelegate.layoutResId -> {
                intervalPacedAdapterDelegate.mode = mode
                intervalPacedAdapterDelegate.onBindViewHolder(items, holder, position)
            }
            regularTrainingAdapterDelegate.layoutResId -> regularTrainingAdapterDelegate.onBindViewHolder(items, holder, position)
            else -> throw DelegateAdapterNotFoundException()
        }
        Log.d("ADAPTER MODE", mode.name)
    }

    override fun getItemCount(): Int = items.size
}