package com.achaka.intervals

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.IntervalItemBinding

class IntervalsAdapter(
//    private val onPlusClicked: AdapterView.OnItemClickListener,
//                       private val onMinusClicked: AdapterView.OnItemClickListener
                       )
    : ListAdapter<Interval, IntervalsAdapter.IntervalViewHolder>(DiffCallback) {

    private val intervals: List<Interval> = mutableListOf()

    fun setData(list: List<Interval>) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntervalViewHolder {
        val viewHolder = IntervalViewHolder(
            IntervalItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
        viewHolder.binding.minus.setOnClickListener {

        }
        viewHolder.binding.plus.setOnClickListener {

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: IntervalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class IntervalViewHolder(internal var binding: IntervalItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(interval: Interval) {
            binding.intervalNumber.text = interval.number.toString()
            binding.intervalDesc.text = interval.description
            binding.suggestedPace.text = interval.suggestedPace
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