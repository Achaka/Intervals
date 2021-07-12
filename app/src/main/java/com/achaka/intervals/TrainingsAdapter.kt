package com.achaka.intervals

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.TrainingItemBinding

class TrainingsAdapter(val onItemClickListener: (Training) -> Unit) :
    ListAdapter<Training, TrainingsAdapter.TrainingsAdapterViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingsAdapterViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TrainingsAdapterViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class TrainingsAdapterViewHolder(val binding: TrainingItemBinding): RecyclerView.ViewHolder() {

    }
}