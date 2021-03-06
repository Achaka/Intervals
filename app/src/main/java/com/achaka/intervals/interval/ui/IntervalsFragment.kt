package com.achaka.intervals.interval.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.achaka.intervals.IntervalsApp
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentIntervalsBinding
import com.achaka.intervals.interval.viewmodel.IntervalsViewModel
import com.achaka.intervals.interval.viewmodel.IntervalsViewModelFactory
import com.achaka.intervals.interval.model.Interval
import com.achaka.intervals.interval.model.IntervalFragmentMode
import com.achaka.intervals.training.viewmodel.TrainingsViewModel
import com.achaka.intervals.training.viewmodel.TrainingsViewModelFactory
import com.achaka.intervals.training.model.Training
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

private const val TRAINING_ID = "trainingId"
private const val INTERVALS_MODE = "intervalsMode"
private const val NEW_TRAINING_NAME = "newTrainingName"

private lateinit var adapter: IntervalsAdapter
private lateinit var recyclerView: RecyclerView

private val initialValues = ArrayList<Int>()

class IntervalsFragment : Fragment(), IntervalsAdapter.DeleteClickListener {

    private lateinit var fab: FloatingActionButton
    private val subscriptions = CompositeDisposable()

    private var sMode: IntervalFragmentMode by Delegates.observable(IntervalFragmentMode.EDIT_MODE) {
            _: KProperty<*>, _: IntervalFragmentMode, _: IntervalFragmentMode ->
        requireActivity().invalidateOptionsMenu()
        setupFab(fab)
    }

    private var sNewTrainingName: String = ""
    private var trainingId: Long = 0
    private val mViewModel: IntervalsViewModel by viewModels {
        IntervalsViewModelFactory(
            (activity?.application as IntervalsApp).database.intervalDao()
        )
    }
    private val mTrainingsViewModel: TrainingsViewModel by viewModels {
        TrainingsViewModelFactory(
            (activity?.application as IntervalsApp).database.trainingDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        val binding = FragmentIntervalsBinding.bind(
            inflater.inflate(
                R.layout.fragment_intervals,
                container,
                false
            )
        )
        fab = binding.addIntervalFab

        //getting arguments after the fab was initialized
        arguments?.let {
            sMode = it.getSerializable(INTERVALS_MODE) as IntervalFragmentMode
            sNewTrainingName = it.getString(NEW_TRAINING_NAME, "")
            trainingId = it.getLong(TRAINING_ID)
        }

        //recyclerView setup
        recyclerView = binding.intervalsRecyclerView
        recyclerView.itemAnimator = null
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = IntervalsAdapter(this)
        recyclerView.adapter = adapter

        //RegularMode by default
        adapter.setMode(IntervalFragmentMode.REGULAR_MODE)

        val intervalsObserver = getIntervals()
        subscriptions.add(intervalsObserver)

        binding.button.setOnClickListener {
            clear()
        }

        return binding.root
    }

    override fun onDestroyView() {
        subscriptions.clear()
        super.onDestroyView()
    }

    private fun setupFab(fab: FloatingActionButton) {
        when (sMode) {
            IntervalFragmentMode.REGULAR_MODE -> {
                fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                fab.setOnClickListener {
                    onPlayClick()
                }
            }
            IntervalFragmentMode.EDIT_MODE, IntervalFragmentMode.NEW_TRAINING_EDIT_MODE -> {
                fab.setImageResource(R.drawable.ic_sharp_add_24)
                fab.setOnClickListener {
                    onAddItemClick(adapter)
                }
            }
            IntervalFragmentMode.RUNNING_MODE -> {
                fab.setImageResource(R.drawable.ic_baseline_stop_24)
                fab.setOnClickListener {
                    onStopClick()
                }
            }
        }
    }

    //Immediately insert default item after the click
    private fun onAddItemClick(adapter: IntervalsAdapter) {
        val currentList = adapter.currentList.toMutableList()
        val defaultInterval = Interval(id = 0L,
            number = currentList.size + 1,
            description =  "",
            seconds = 5,
            isCompleted = false,
            trainingId = trainingId,
            suggestedPace = "0:00",
            progress = 0)
        currentList.add(defaultInterval)
        if (sMode == IntervalFragmentMode.EDIT_MODE) {
            insertNewInterval(defaultInterval)
        }
        adapter.submitList(currentList)
    }

    private var timer: Timer? = null

    private fun onPlayClick(
    ) {
        sMode = IntervalFragmentMode.RUNNING_MODE
        //start Pos is always 0
        startCountdownTimer(cp = 0)
    }

    fun startCountdownTimer(cp: Int) {
        val timeToGo = adapter.currentList[cp].seconds
        timer = Timer(timeToGo, cp)
        timer?.start()
    }

    private fun onStopClick() {
        sMode = IntervalFragmentMode.REGULAR_MODE
        timer?.cancel()
        resetAdapter()
    }


// MENU
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.inteval_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        when (sMode) {
            IntervalFragmentMode.EDIT_MODE, IntervalFragmentMode.NEW_TRAINING_EDIT_MODE -> {
                menu.findItem(R.id.edit_intervals).isVisible = false
                menu.findItem(R.id.confirm).isVisible = true
                adapter.setMode(sMode)
                recyclerView.adapter = adapter

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
                if (sMode == IntervalFragmentMode.NEW_TRAINING_EDIT_MODE
                    && sNewTrainingName.isNotEmpty()) {
                    insertIntervalsWithTraining(sNewTrainingName)
                }
                updateIntervals(adapter.currentList)
                sMode = IntervalFragmentMode.REGULAR_MODE
                adapter.setMode(sMode)
                recyclerView.adapter = adapter
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //VIEW MODEL
    /*
   * ?????????????? ???????????? ??????????????????
   */
    private fun insertNewInterval(interval: Interval) {
        val insertSub = mViewModel.insertInterval(interval)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //do nothing
            }, {
                Toast.makeText(this.requireContext(), getString(R.string.unknownError), Toast.LENGTH_SHORT).show()
            })
        subscriptions.add(insertSub)
     }
    /*
    * ?????????????? ???????????? ???????????????????? - ???????????????????????? ?????? ???????????????? ?????????? ????????????????????
    */
    private fun insertNewIntervals(trainingId: Long) {
        val intervals = adapter.currentList
        intervals.forEach { it.trainingId = trainingId }
        val insertIntervalsSub = mViewModel.insertIntervals(intervals)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val intervalsObserver = getIntervals()
                subscriptions.add(intervalsObserver)
            }, {
                Toast.makeText(this.requireContext(), getString(R.string.unknownError), Toast.LENGTH_SHORT).show()
            })
        subscriptions.add(insertIntervalsSub)
    }
    /*
    * ?????????????? ???????????? ???????????????????? ?? ???????????????? ?????????? ????????????????????
    */
    private fun insertIntervalsWithTraining(trainingName: String?) {
        if (trainingName!=null) {
            val insertSub = mTrainingsViewModel.insertTraining(Training(0L, trainingName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {
                    insertNewIntervals(it)
                    trainingId = it
                    getIntervals()
                },
                {
                    Toast.makeText(this.requireContext(), getString(R.string.unknownError), Toast.LENGTH_SHORT).show()
                }
            )
            subscriptions.add(insertSub)
        }
    }

    private fun getIntervals() =
        mViewModel.getIntervals(trainingId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    initialValues.clear()
                    it.forEach { initialValues.add(it.seconds) }
                    adapter.submitList(it)
                },
                {
                    Toast.makeText(this.context, getString(R.string.intervalsLoadError), Toast.LENGTH_SHORT).show()
                }
            )

    private fun updateIntervals(list: List<Interval>) {
        val updateSub = mViewModel.updateIntervals(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //do nothing
            },{
                Toast.makeText(this.requireContext(), getString(R.string.unknownError), Toast.LENGTH_SHORT).show()
            })
        subscriptions.add(updateSub)
    }

    override fun deleteInterval(intervalId: Long) {
        val currentList = adapter.currentList.toMutableList()

        fun removeItem() {
            currentList.removeIf{ it.id == intervalId }
            currentList.forEachIndexed{index, interval -> interval.number = index+1 }
        }

        when(sMode) {
            IntervalFragmentMode.NEW_TRAINING_EDIT_MODE -> {
                removeItem()
                adapter.submitList(currentList)
                adapter.notifyDataSetChanged()
            }
            else -> {
                removeItem()
                val deleteIntervalSub = mViewModel.deleteInterval(intervalId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        updateIntervals(currentList)
                        adapter.notifyDataSetChanged()
                    }, {
                        Toast.makeText(this.requireContext(), getString(R.string.unknownError), Toast.LENGTH_SHORT).show()
                    })
                subscriptions.add(deleteIntervalSub)
            }
        }
    }
    fun clear() {
        trainingId.let { mViewModel.clear(it) }
    }


/////////////////////////////////////////
////////******TIMER
//

 inner class Timer(timeToGo: Int, var cp: Int) : CountDownTimer(timeToGo*1000L, 25) {

        private val initialTimerValues = adapter.currentList.map { interval -> interval.seconds }
        override fun onTick(millisUntilFinished: Long) {
            when (millisUntilFinished%1000<50) {
                true -> {
                    /*
                     * ???????????????????? ?????????????????????? ??????????????
                    */
                    val toGo = (millisUntilFinished / 1000).toInt()
                    adapter.currentList[cp].seconds = toGo
                    /*
                     * * ???????????????????? progressIndicator
                    */
                    adapter.currentList[cp].progress = 100-((toGo.toFloat()/initialTimerValues[cp])*100).toInt()
                    adapter.notifyItemChanged(cp)
                }
            }
            if (millisUntilFinished == 0L) {
                adapter.currentList[cp].seconds = (millisUntilFinished/1000).toInt()
                adapter.notifyItemChanged(cp)
            }
        }
        override fun onFinish() {
            if (cp < adapter.currentList.size - 1) {
                startCountdownTimer(cp+1)
            } else {
                sMode = IntervalFragmentMode.REGULAR_MODE
                resetAdapter()
            }
        }
    }

    /*
    * * ?????????? ???????????????? ???? ?????????????????? ????????????????
    */
    private fun resetAdapter() {
        val list = adapter.currentList
        list.forEachIndexed {index, interval -> interval.seconds = initialValues[index] }
        list.forEach { interval -> interval.progress = 0 }
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }
}