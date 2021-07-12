package com.achaka.intervals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.IntervalItemBinding

class IntervalsAdapter : ListAdapter<Interval, IntervalsAdapter.IntervalViewHolder>(DiffCallback) {

    private val intervals: List<Interval> = mutableListOf()

    fun setData(list: List<Interval>) {

    }

    class IntervalViewHolder(private var binding: IntervalItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(interval: Interval) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntervalViewHolder {
        return IntervalViewHolder(
            IntervalItemBinding.inflate(
                LayoutInflater.from(parent.context),
            )
        )
    }

    override fun onBindViewHolder(holder: IntervalViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}