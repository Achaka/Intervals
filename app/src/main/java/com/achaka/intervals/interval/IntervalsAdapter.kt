package com.achaka.intervals.interval

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.databinding.IntervalItemBinding

class IntervalsAdapter(
    private val onPlusClicked: (Interval) -> Unit,
    private val onMinusClicked: (Interval) -> Unit,
    private val onItemLongPress: (Interval) -> Unit,
//    ///////////////////////////////////////////////////
//    ///*****UPDATE AND DELETE OPERATIONS RESERVED FOR FUTURE
//    ///////////////////////////////////////////////////
//    val updateListener: UpdateListener,
//    private val temporaryOnDeleteClicked: (Interval) -> Unit,
    )
    : ListAdapter<Interval, IntervalsAdapter.IntervalViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntervalViewHolder {

       val intervalViewHolder = IntervalViewHolder(IntervalItemBinding.inflate(LayoutInflater.from(parent.context)),
           /////////////////////////////////////////////////////
           /////*****UPDATE AND DELETE OPERATIONS RESERVED FOR FUTURE
           /////////////////////////////////////////////////////
//           UpdateWatcher(currentList, updateListener)
       )
       return intervalViewHolder
    }

    override fun onBindViewHolder(holder: IntervalViewHolder, position: Int) {
        /////////////////////////////////////////////////////
        /////*****UPDATE AND DELETE OPERATIONS RESERVED FOR FUTURE
        /////////////////////////////////////////////////////
//        holder.updateWatcher.setId(getItem(position).id.toInt())
        holder.setId(getItem(position).id.toInt())
        holder.bind(getItem(position))
    }

    inner class IntervalViewHolder(val binding: IntervalItemBinding,
        /////////////////////////////////////////////////////
        /////*****UPDATE AND DELETE OPERATIONS RESERVED FOR FUTURE
        /////////////////////////////////////////////////////
//                                   val updateWatcher: UpdateWatcher
                                   ): RecyclerView.ViewHolder(binding.root) {
        private var id: Int = 0
        fun setId(id: Int) {
            this.id = id
        }

        /////////////////////////////////////////////////////
        /////*****UPDATE AND DELETE OPERATIONS RESERVED FOR FUTURE
        /////////////////////////////////////////////////////
//        init {
//            val timeWatcher = UpdateWatcher(currentList, updateListener)
//            timeWatcher.setId(id)
//            binding.intervalDesc.addTextChangedListener(updateWatcher)
//            binding.timeToGo.addTextChangedListener(
//                object : TextWatcher {
//
//                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                    }
//
//                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                    }
//
//                    override fun afterTextChanged(p0: Editable?) {
//                        updateListener.updateTimeToGoById(id, p0.toString(), currentList)
//                    }
//
//                }
////                UpdateWatcher(currentList, updateListener)
//            )
//
//            binding.suggestedPace.addTextChangedListener(object : TextWatcher {
//
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                }
//
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                }
//
//                override fun afterTextChanged(p0: Editable?) {
//                    updateListener.updateSuggestedPaceById(id, p0.toString(), currentList)
//                }
//
//            })
//            binding.intervalNumber.addTextChangedListener(object : TextWatcher {
//
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                }
//
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                }
//
//                override fun afterTextChanged(p0: Editable?) {
//                    updateListener.updateNumberById(id, p0.toString().toInt(), currentList)
//                }

//            })
//        }
        fun bind(interval: Interval) {

            binding.intervalNumber.text = interval.number.toString()

            binding.intervalDesc.text = SpannableStringBuilder.valueOf(interval.description)
            binding.minus.setOnClickListener {
                binding.timeToGo
            }
            binding.plus.setOnClickListener {
                binding.timeToGo
            }

            binding.timeToGo.text?.clear()
            binding.timeToGo.text?.append(interval.seconds.toString())

            binding.suggestedPace.text?.clear()
            binding.suggestedPace.text?.append(interval.suggestedPace)

            binding.status.setOnClickListener {
//                temporaryOnDeleteClicked(getItem(adapterPosition))
            }

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
    /////////////////////////////////////////////////////
    /////*****UPDATE AND DELETE OPERATIONS RESERVED FOR FUTURE
    /////////////////////////////////////////////////////
//    class UpdateWatcher(private val currentList: List<Interval>, private val updateListener: UpdateListener): TextWatcher {
//
//        //make nullable
//        private var intervalId: Int = 0
//
//        fun setId(intervalId: Int) {
//            this.intervalId = intervalId
//        }
//
//        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//        }
//
//        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//        }
//
//        override fun afterTextChanged(currentDesc: Editable?) {
//            updateListener.updateFieldById(intervalId, currentDesc.toString(), currentList)
//        }
//    }

//    interface UpdateListener {
//        fun updateFieldById(intervalId: Int, currentDesc: String, currentList: List<Interval>)
//        fun updateTimeToGoById(intervalId: Int, currentTimeToGo: String, currentList: List<Interval>)
//        fun updateSuggestedPaceById(
//            intervalId: Int,
//            currentSuggestedPace: String,
//            currentList: List<Interval>
//        )
//        fun updateNumberById(intervalId: Int, newValue: Int, currentList: List<Interval>)
//    }

    interface Count {fun countDown(view: View)}
}
