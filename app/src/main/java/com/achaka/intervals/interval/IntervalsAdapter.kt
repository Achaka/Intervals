package com.achaka.intervals.interval

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.IntervalItemBinding

class IntervalsAdapter(
    private val onPlusClicked: (Interval) -> Unit,
    private val onMinusClicked: (Interval) -> Unit,
    private val onItemLongPress: (Interval) -> Unit
    )
    : ListAdapter<Interval, IntervalsAdapter.IntervalViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntervalViewHolder {
        val intervalViewHolder = IntervalViewHolder(IntervalItemBinding.inflate(LayoutInflater.from(parent.context)))
        return intervalViewHolder
    }

    override fun onBindViewHolder(holder: IntervalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class IntervalViewHolder(private var binding: IntervalItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(interval: Interval) {
            binding.intervalNumber.text = interval.number.toString()
            binding.minus.setOnClickListener {
                binding.timeToGo
            }
            binding.timeToGo.text?.clear()
            binding.timeToGo.text?.append(interval.seconds.toString())
            binding.minus.setOnClickListener {
                binding.timeToGo
            }
            binding.suggestedPace.text?.clear()
            binding.suggestedPace.text?.append(interval.suggestedPace)
            if (interval.isCompleted) {
                binding.status
            } else {
                binding.status
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Interval>() {
            override fun areItemsTheSame(oldItem: Interval, newItem: Interval): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Interval, newItem: Interval): Boolean {
                return oldItem == newItem
            }

        }
    }
}