package com.achaka.intervals.paceconverter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.PaceHistoryItemBinding

class PaceConverterAdapter: RecyclerView.Adapter<PaceConverterAdapter.PaceConverterViewHolder>() {

    var history = mutableListOf<Pace>()

    fun setData(list: MutableList<Pace>) {
        this.history = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaceConverterViewHolder {
        val viewHolder = PaceConverterViewHolder(PaceHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
        ))

        return viewHolder
    }

    override fun onBindViewHolder(holder: PaceConverterViewHolder, position: Int) {
        holder.bind(history[position])
    }

    override fun getItemCount(): Int = history.size

    class PaceConverterViewHolder(private val binding: PaceHistoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pace: Pace) {
            binding
        }
    }
}