package com.achaka.intervals.interval

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.intervals.IntervalsApp
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentIntervalsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TRAINING_ID = "training_id"
private var RUN_MODE: Boolean = false

class IntervalsFragment : Fragment() {

    private var trainingId: Long? = null

    private val mViewModel: IntervalsViewModel by activityViewModels {
        IntervalsViewModelFactory(
            (activity?.application as IntervalsApp).database.intervalDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trainingId = it.getLong(TRAINING_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Toast.makeText(this.context, ""+trainingId, Toast.LENGTH_SHORT).show()

        val binding = FragmentIntervalsBinding.bind(inflater.inflate(R.layout.fragment_intervals, container, false))
        val recyclerView = binding.intervalsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = IntervalsAdapter({
            //increment duration counter
        },        {
            //decrement duration counter
        },        {
            //unblock edit text and other fields
            //update in database
        }
        )

        if (adapter.currentList.isEmpty()) {
            recyclerView.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE

        } else {
            recyclerView.visibility = View.INVISIBLE
            binding.intervalsRecyclerView.visibility = View.GONE
        }
        recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            mViewModel.getIntervals(0).collect {
                adapter.submitList(it)
            }
        }
        binding.emptyView.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
            val listTest = mutableListOf(Interval(0L, 1, "ddd", 90, false, 0L, "0:00"))
            adapter.submitList(listTest)
        }

        binding.addIntervalFab.setOnClickListener {
            val currentList = adapter.currentList.toMutableList()
            currentList.add( Interval(0L, currentList.size, "ddd", 90, false, 0L, "0:00"))
            adapter.submitList(currentList)
        }



        return binding.root
    }

    fun incrementDurationCounter() {

    }

    fun decrementDurationCounter() {

    }

    fun updateInterval(intervalId: Long) {

    }
}