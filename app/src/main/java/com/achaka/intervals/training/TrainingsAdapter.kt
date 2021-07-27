package com.achaka.intervals.training

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.TrainingItemBinding

class TrainingsAdapter(private val onItemClickListener: (Training) -> Unit) :
    ListAdapter<Training, TrainingsAdapter.TrainingsAdapterViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingsAdapterViewHolder {
        val viewHolder = TrainingsAdapterViewHolder(TrainingItemBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
        viewHolder.itemView.setOnClickListener {
            onItemClickListener(getItem(viewHolder.adapterPosition))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: TrainingsAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TrainingsAdapterViewHolder(private var binding: TrainingItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(training: Training) {
            binding.name.text = training.trainingName + " " + training.id
        }
    }

    //This is just an object that helps the ListAdapter determine which items in the new and old lists
    // are different when updating the list.
    // There are two methods: areItemsTheSame() checks if the object (or row in the database in your case)
    // is the same by only checking the ID. areContentsTheSame() checks if all properties, not just the ID, are the same.
    // These methods allow the ListAdapter to determine which items have been inserted, updated,
    // and deleted so that the UI can be updated accordingly.
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Training>(){
            override fun areItemsTheSame(oldItem: Training, newItem: Training): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Training, newItem: Training): Boolean {
                return oldItem == newItem
            }

        }
    }
}