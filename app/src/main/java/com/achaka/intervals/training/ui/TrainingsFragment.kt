package com.achaka.intervals.training.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.intervals.IntervalsApp
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentTrainingsBinding
import com.achaka.intervals.di.viewmodel.ViewModelFactory
import com.achaka.intervals.training.model.TrainingRepository
import com.achaka.intervals.training.viewmodel.TrainingsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class TrainingsFragment : Fragment() {

    private val subscriptions = CompositeDisposable()

    @Inject
    lateinit var repository: TrainingRepository

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var trainingsViewModel: TrainingsViewModel

    private var _binding: FragmentTrainingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as IntervalsApp).trainingComponent.inject(this)
        trainingsViewModel =
            ViewModelProvider(this, viewModelFactory).get(TrainingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.title = getString(R.string.fragment_trainings_title)

        _binding = FragmentTrainingsBinding.bind(
            inflater.inflate(
                R.layout.fragment_trainings,
                container,
                false
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.trainingsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = TrainingsAdapter({
            Log.d("training pass", it.toString())
            val action = TrainingsFragmentDirections.actionTrainingsFragmentToIntervalsFragment(
                trainingId = it.id
            )
            view.findNavController().navigate(action)
        }, {
            val deleteSub = trainingsViewModel.deleteTraining(it)
                .subscribeOn(Schedulers.io())
                .subscribe({

                }, {
                    Toast.makeText(
                        this.context,
                        getString(R.string.unknownError),
                        Toast.LENGTH_SHORT
                    ).show()
                })
            subscriptions.add(deleteSub)
        }
        )

        binding.addTrainingFab.setImageResource(R.drawable.ic_sharp_add_24)
        binding.addTrainingFab.setOnClickListener {
            val action =
                TrainingsFragmentDirections.actionTrainingsFragmentToInsertTrainingFragment()
            view.findNavController().navigate(action)
        }

        recyclerView.adapter = adapter

        val trainingsObserver = trainingsViewModel.getTrainings()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    adapter.submitList(it)
                },
                {
                    Toast.makeText(
                        this.context,
                        getString(R.string.trainingsLoadError),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

        subscriptions.add(trainingsObserver)
    }

    override fun onDestroyView() {
        subscriptions.clear()
        _binding = null
        super.onDestroyView()
    }
}