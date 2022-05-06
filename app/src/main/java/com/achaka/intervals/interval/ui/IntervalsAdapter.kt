package com.achaka.intervals.interval.ui

import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.IntervalPacedItemBinding
import com.achaka.intervals.interval.model.Interval
import com.achaka.intervals.interval.model.IntervalFragmentMode
import java.lang.NumberFormatException
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class IntervalsAdapter(private val deleteClickListener: DeleteClickListener)
    : ListAdapter<Interval, IntervalsAdapter.IntervalViewHolder>(DiffCallback) {

    private var sMode: IntervalFragmentMode by Delegates.observable(IntervalFragmentMode.EDIT_MODE) {
            _: KProperty<*>, _: IntervalFragmentMode, _: IntervalFragmentMode ->
    }

    fun setMode(mode: IntervalFragmentMode) {
        this.sMode = mode
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntervalViewHolder {
        val binding = IntervalPacedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntervalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntervalViewHolder, position: Int) {
        holder.setId(getItem(position).id.toInt())
        holder.bind(getItem(position))
    }

    inner class IntervalViewHolder(private val binding: IntervalPacedItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        private var id: Int = 0
        fun setId(id: Int) {
            this.id = id
        }

        fun bind(interval: Interval) {
            when(sMode) {
                IntervalFragmentMode.EDIT_MODE, IntervalFragmentMode.NEW_TRAINING_EDIT_MODE -> {
                    binding.delete.visibility = View.VISIBLE
                    binding.intervalDesc.isEnabled = true
                    binding.intervalDesc.addTextChangedListener(object: TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            //do nothing
                        }
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            //do nothing
                        }
                        override fun afterTextChanged(p0: Editable?) {
                            interval.description = p0.toString()
                        }
                    })
                    binding.suggestedPace.isEnabled = true
                    binding.suggestedPace.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            //do nothing
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            //do nothing
                        }

                        override fun afterTextChanged(p0: Editable?) {
                            interval.suggestedPace = p0.toString()
                        }
                    })
                    binding.timeToGo.isEnabled = true
                    binding.timeToGo.addTextChangedListener(object: TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            //do nothing
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            //do nothing
                        }

                        override fun afterTextChanged(p0: Editable?) {
                            val fieldValue = p0.toString()
                            try {
                                interval.seconds = fieldValue.toInt()
                            } catch (e: NumberFormatException) {
                                //do nothing
                            }

                        }

                    })
                }
                else -> {
                    binding.delete.visibility = View.INVISIBLE
                    binding.intervalDesc.isEnabled = false
                    binding.suggestedPace.isEnabled = false
                    binding.timeToGo.isEnabled = false
                }
            }

            binding.number.text = interval.number.toString()

            binding.intervalDesc.text = SpannableStringBuilder.valueOf(interval.description)

            binding.timeToGo.text?.clear()
            binding.timeToGo.text?.append(interval.seconds.toString())

            binding.linearProgressIndicator.progress = interval.progress

            binding.suggestedPace.text?.clear()
            binding.suggestedPace.text?.append(interval.suggestedPace)

            binding.delete.setOnClickListener {
                deleteClickListener.deleteInterval(interval.id)
            }
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Interval>() {
            override fun areItemsTheSame(oldItem: Interval, newItem: Interval): Boolean {
                Log.d("are items the same", (oldItem.id == newItem.id).toString())
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Interval, newItem: Interval): Boolean {
                Log.d("are contents the same", (oldItem == newItem).toString())
                return oldItem == newItem
            }
        }
    }

    interface DeleteClickListener {
        fun deleteInterval(intervalId: Long)
    }

}
