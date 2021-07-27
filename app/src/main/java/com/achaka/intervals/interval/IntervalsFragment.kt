package com.achaka.intervals.interval

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.intervals.IntervalsApp
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentIntervalsBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

private const val TRAINING_ID = "trainingId"
//change to observable
private var sMode: IntervalFragmentMode = IntervalFragmentMode.REGULAR_MODE
private lateinit var adapter: IntervalsAdapter
private val initialList = mutableListOf<Interval>()

class IntervalsFragment : Fragment() {

    private var trainingId: Long = 0



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
        lifecycle.coroutineScope.launch {
            trainingId?.let {
                mViewModel.getIntervals(it).collect { initialList.addAll(it) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        val binding = FragmentIntervalsBinding.bind(inflater.inflate(R.layout.fragment_intervals, container, false))
        val recyclerView = binding.intervalsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = IntervalsAdapter({
            //increment duration counter
        },        {
            //decrement duration counter
        },        {
            //unblock edit text and other fields
            //update in database
        }
        )

        recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            mViewModel.getIntervals(trainingId).collect {
                adapter.submitList(it)
            }
        }

        if (adapter.currentList.isEmpty()) {
            recyclerView.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
            sMode = IntervalFragmentMode.EDIT_MODE

        } else {
            recyclerView.visibility = View.INVISIBLE
            binding.intervalsRecyclerView.visibility = View.GONE
            sMode = IntervalFragmentMode.REGULAR_MODE
        }

        binding.emptyView.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
            val listTest = mutableListOf(Interval(0L, 1, "ddd", 90, false, trainingId, "0:00"))
            adapter.submitList(listTest)
        }

        binding.button.setOnClickListener {
            clear()
        }

        val fab = binding.addIntervalFab
        when (sMode) {
            IntervalFragmentMode.REGULAR_MODE -> {
                fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                fab.setOnClickListener {
                    sMode = IntervalFragmentMode.RUNNING_MODE
                }
            }
            IntervalFragmentMode.EDIT_MODE -> {
                fab.setImageResource(R.drawable.ic_sharp_add_24)
                fab.setOnClickListener {
                    onAddItemClick(adapter)
                }
            }
            IntervalFragmentMode.RUNNING_MODE -> {
                fab.setImageResource(R.drawable.ic_baseline_pause_24)
                fab.setOnClickListener {
                    sMode = IntervalFragmentMode.PAUSE
                    onPlayClick()
                }
            }
//            IntervalFragmentMode.PAUSE -> {
//                fab.setI
//                fab.setOnClickListener {
//                    onPauseClick()
//                }
//            }
            IntervalFragmentMode.STOP -> {
                fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                fab.setOnClickListener {
                    onStopClick()
                }
            }
        }

        adapter.currentList


        return binding.root
    }

    fun incrementDurationCounter() {

    }

    fun decrementDurationCounter() {

    }

    fun updateInterval(intervalId: Long) {

    }

    fun onAddItemClick(adapter: IntervalsAdapter) {
        val currentList = adapter.currentList.toMutableList()
        currentList.add( Interval(0L, currentList.size, "", 90, false, trainingId, "0:00"))
        adapter.submitList(currentList)
    }

    private fun onPlayClick() {

    }

    private fun onPauseClick() {

    }

    private fun onStopClick() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.inteval_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        when (sMode) {
        IntervalFragmentMode.EDIT_MODE -> {
            Toast.makeText(context, sMode.name, Toast.LENGTH_SHORT).show()
            menu.findItem(R.id.edit_intervals).isVisible = false
            menu.findItem(R.id.confirm).isVisible = true
            }
         IntervalFragmentMode.REGULAR_MODE -> {
             menu.findItem(R.id.edit_intervals).isVisible = true
             menu.findItem(R.id.confirm).isVisible = false
            }
         else -> {
             menu.findItem(R.id.edit_intervals).isVisible = false
             menu.findItem(R.id.confirm).isVisible = false
         }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit_intervals -> {
                sMode = IntervalFragmentMode.EDIT_MODE
            }
            R.id.confirm -> {
                mViewModel.insertIntervals(getDifference(initialList, adapter.currentList))
                sMode = IntervalFragmentMode.REGULAR_MODE
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDifference(currentList: List<Interval>, newList: List<Interval>): List<Interval> {
        val diff1 = currentList.filter { !newList.contains(it) }
        val diff2 = newList.filter { !currentList.contains(it) }
        return diff1.plus(diff2)
    }

    fun clear() {
        trainingId?.let { mViewModel.clear(it) }
    }
}