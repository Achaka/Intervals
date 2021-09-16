package com.achaka.intervals.interval

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.intervals.IntervalsApp
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentIntervalsBinding
import com.achaka.intervals.paceconverter.PaceConverter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

private const val TRAINING_ID = "trainingId"
//change to observable

//
private lateinit var adapter: IntervalsAdapter
private val initialList = mutableListOf<Interval>()

class IntervalsFragment : Fragment()
///////***UPDATE FIELDS FOR FUTURE
//    IntervalsAdapter.UpdateListener
{

//    private val listOfUpdates = mutableListOf<Interval>()
//    private val listOfUpdateId = mutableListOf<Int>()
//    private val listToDelete = mutableListOf<Interval>()
//    private val listToInsert = mutableListOf<Interval>()

    private lateinit var fab: FloatingActionButton

    private var sMode: IntervalFragmentMode by Delegates.observable(IntervalFragmentMode.EDIT_MODE) {
            property: KProperty<*>, oldValue: IntervalFragmentMode, newValue: IntervalFragmentMode ->
        Log.d("MODE", newValue.name)
        requireActivity().invalidateOptionsMenu()
        setupFab(fab)
    }

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        val binding = FragmentIntervalsBinding.bind(
            inflater.inflate(
                R.layout.fragment_intervals,
                container,
                false
            )
        )
        fab = binding.addIntervalFab

        val recyclerView = binding.intervalsRecyclerView
        recyclerView.itemAnimator = null
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = IntervalsAdapter({
            //increment duration counter
        }, {
            //decrement duration counter
        }, {
            //unblock edit text and other fields
            //update in database
        },
//            requireContext(),
//            this,
        )

        recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            mViewModel.getIntervals(trainingId).collect {
                adapter.submitList(it)
            }
        }

        recyclerView.visibility = View.VISIBLE
        binding.emptyView.visibility = View.GONE
        sMode = IntervalFragmentMode.REGULAR_MODE

        binding.emptyView.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
            val listTest = mutableListOf(Interval(0L, 1, "ddd", 5, false, trainingId, "0:00"))
            adapter.submitList(listTest)
        }

        binding.button.setOnClickListener {
            clear()
        }

        adapter.currentList

        return binding.root
    }

    fun setupFab(fab: FloatingActionButton) {
        when (sMode) {
            IntervalFragmentMode.REGULAR_MODE -> {
                fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                fab.setOnClickListener {
                    onPlayClick()
                }
            }
            IntervalFragmentMode.EDIT_MODE -> {
                fab.setImageResource(R.drawable.ic_sharp_add_24)
                fab.setOnClickListener {
                    onAddItemClick(adapter)
                }
            }
            IntervalFragmentMode.RUNNING_MODE -> {
                //button is stop
                fab.setImageResource(R.drawable.ic_baseline_stop_24)
                fab.setOnClickListener {
                    onStopClick()
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

                }
            }
        }
    }

    fun incrementDurationCounter() {

    }

    fun decrementDurationCounter() {

    }

    fun updateInterval(intervalId: Long) {

    }

    fun onAddItemClick(adapter: IntervalsAdapter) {
        val currentList = adapter.currentList.toMutableList()
        val defaultInterval = Interval(0L, currentList.size + 1, "", 5, false, trainingId, "0:00")
        currentList.add(defaultInterval)
        insertNewInterval(defaultInterval)
        adapter.submitList(currentList)
    }



    private fun onPlayClick(
    ) {
        sMode = IntervalFragmentMode.RUNNING_MODE
        //start Pos always 0
        var currentPosition = 0
        val timeToGo = adapter.currentList[0].seconds
        startCountdownTimer(timeToGo, 0)
    }

//    private fun onPauseClick() {
//
//    }

    private fun onStopClick() {
        sMode = IntervalFragmentMode.REGULAR_MODE
        //reset counters
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.inteval_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        when (sMode) {
            IntervalFragmentMode.EDIT_MODE -> {
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
        when (item.itemId) {
            R.id.edit_intervals -> {
                sMode = IntervalFragmentMode.EDIT_MODE
            }
            R.id.confirm -> {
//                insertNewIntervals()
//                insertNewIntervals(getInsertedIntervals(adapter.currentList))
                sMode = IntervalFragmentMode.REGULAR_MODE
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun insertNewInterval(interval: Interval) {
        mViewModel.insertInterval(interval)
    }

//    fun insertNewIntervals(list: List<Interval>) {
//        mViewModel.insertIntervals(list)
//    }
//
//    fun updateIntervals(list: List<Interval>) {
//        mViewModel.updateIntervals(list)
//    }

    fun updateInterval(interval: Interval) {
        mViewModel.updateInterval(interval)
    }

//    fun deleteIntervals(list: List<Interval>) {
//        mViewModel
//    }
//
    fun deleteInterval(interval: Interval) {

    }
    fun clear() {
        trainingId.let { mViewModel.clear(it) }
    }


/////////////////////////////////////////
////////******TIMER
//

    fun startCountdownTimer(timeToGo: Int, cp: Int) {
        val currentPosition = cp

        var countDownTimer = object : CountDownTimer(timeToGo*1000L, 50) {
            override fun onTick(millisUntilFinished: Long) {

                when (millisUntilFinished%1000<50) {
                    true -> {
                        adapter.currentList[currentPosition].seconds =
                        (millisUntilFinished / 1000).toInt()
                        Log.d ("TIME to go" + currentPosition + ":", (millisUntilFinished).toString())
                        adapter.notifyItemChanged(currentPosition)
                    }
                }
                if (millisUntilFinished == 0L) {
                    adapter.currentList[currentPosition].seconds = (millisUntilFinished/1000).toInt()
                    adapter.notifyItemChanged(currentPosition)
                }
            }
            override fun onFinish() {
                Log.d("TIMER", "finished")
                if (currentPosition < adapter.currentList.size-1) {
                    startCountdownTimer(adapter.currentList[currentPosition+1].seconds, currentPosition+1)
                } else {
//                    resetAdapter()
                    sMode = IntervalFragmentMode.REGULAR_MODE
                }
            }
        }.start()
    }

/////////////////////////////////////////
////////******HELPER METHODS FOR DATABASE
//



    /////////////////////////////////////////////////////
    /////*****UPDATE AND DELETE OPERATIONS RESERVED FOR FUTURE
    /////////////////////////////////////////////////////

//    fun updateIntervalDescById(intervalId: Int, newDesc: String) {
//        mViewModel.updateDescIntervalsById(intervalId, newDesc)
//    }

//    override fun updateNumberById(intervalId: Int, newValue: Int, currentList: List<Interval>) {
//        if (intervalId != 0) {
//            val initialInterval = currentList.first { it.id == intervalId.toLong() }
//            if (initialInterval.number != newValue) {
//                //add to list or set flag
//                if (listOfUpdates.none { it.id == intervalId.toLong() }) {
//                    listOfUpdates.add(initialInterval.copy(number = newValue))
//                }
//                Log.d("numberUpdated", "true")
//                Log.d("init", initialInterval.id.toString())
//                Log.d("initNum", initialInterval.description)
//                Log.d("curr", intervalId.toString())
//                Log.d("currNum", newValue.toString())
//                Log.d("listofupdates", listOfUpdates.size.toString())
//            } else {
//                Log.d("numberUpdated", "false")
//            }
//        }
//    }

    //works
//    override fun updateFieldById(intervalId: Int, newValue: String, currentList: List<Interval>) {
//        if (intervalId != 0) {
//            val initialInterval = currentList.first { it.id == intervalId.toLong() }
//            if (initialInterval.description != newValue) {
//                //add to list or set flag
//                if (listOfUpdates.none { it.id == intervalId.toLong() }) {
//                    listOfUpdates.add(initialInterval.copy(description = newValue))
//                }
//                Log.d("descUpdated", "true")
//                Log.d("init", initialInterval.id.toString())
//                Log.d("initDesc", initialInterval.description)
//                Log.d("curr", intervalId.toString())
//                Log.d("currDesc", newValue)
//                Log.d("listofupdates", listOfUpdates.size.toString())
//            } else {
//                Log.d("descUpdated", "false")
//            }
//        }
//    }

//    override fun updateTimeToGoById(
//        intervalId: Int,
//        currentTimeToGo: String,
//        currentList: List<Interval>
//    ) {
//        if (intervalId != 0 && currentTimeToGo.isNotEmpty()) {
//            val initialInterval = currentList.first { it.id == intervalId.toLong() }
//            if (initialInterval.seconds != currentTimeToGo.toInt()) {
//                //add to list or set flag
//                if (listOfUpdates.none { it.id == intervalId.toLong() }) {
//                    listOfUpdates.add(initialInterval.copy(seconds = currentTimeToGo.toInt()))
//                }
//                Log.d("timetogoUpdated", "true")
//                Log.d("init", initialInterval.id.toString())
//                Log.d("initTimeToGo", initialInterval.seconds.toString())
//                Log.d("curr", intervalId.toString())
//                Log.d("currtimetogo", currentTimeToGo)
//                Log.d("listofupdates", listOfUpdates.size.toString())
//            } else {
//                Log.d("timetogoUpdated", "false")
//            }
//        }
//    }

//    override fun updateSuggestedPaceById(
//        intervalId: Int,
//        currentSuggestedPace: String,
//        currentList: List<Interval>
//    ) {
//        if (intervalId != 0 && currentSuggestedPace.isNotEmpty()) {
//            val initialInterval = currentList.first { it.id == intervalId.toLong() }
//            if (initialInterval.suggestedPace != currentSuggestedPace) {
//                //add to list or set flag
//                if (listOfUpdates.none { it.id == intervalId.toLong() }) {
//                    //MAP of id+newValue
//                    listOfUpdates.add(initialInterval.copy(suggestedPace = currentSuggestedPace))
//                }
//                Log.d("SuggestedPaceUpdated", "true")
//                Log.d("init", initialInterval.id.toString())
//                Log.d("initsuggestedpace", initialInterval.suggestedPace)
//                Log.d("curr", intervalId.toString())
//                Log.d("currentSuggestedPace", currentSuggestedPace)
//                Log.d("listofupdates", listOfUpdates.size.toString())
//            } else {
//                Log.d("SuggestedPaceUpdated", "false")
//            }
//        }
//    }

//    fun getDeletedIntervals(
//        currentList: List<Interval>,
//        newList: List<Interval>
//    ): MutableList<Interval> {
//        val deleted = mutableListOf<Interval>()
//        currentList.forEach { currentItem ->
//            deleted.addAll(
//                newList.filter { (it.id != currentItem.id) }
//            )
//        }
//        deleted.forEach {
//            Log.d("DEL", it.number.toString())
//        }
//        return deleted
//    }

}