package com.achaka.intervals.training

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.intervals.IntervalsApp
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentTrainingsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TrainingsFragment : Fragment() {

    private val mViewModel: TrainingsViewModel by activityViewModels {
        TrainingsViewModelFactory(
            (activity?.application as IntervalsApp).database.trainingDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTrainingsBinding.bind(inflater.inflate(R.layout.fragment_trainings, container, false))

        val recyclerView = binding.trainingsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = TrainingsAdapter {
            val action = TrainingsFragmentDirections.actionTrainingsFragmentToIntervalsFragment(
                trainingId = it.id
            )
            view?.findNavController()?.navigate(action)
        }

        binding.addTrainingFab.setOnClickListener {
            val currentList = adapter.currentList.toMutableList()
            currentList.add(Training(0, "name"))
            adapter.submitList(currentList)

//            val action = TrainingsFragmentDirections.actionTrainingsFragmentToInsertTrainingFragment()
//            view?.findNavController()?.navigate(action)
        }

        recyclerView.adapter = adapter
        lifecycle.coroutineScope.launch {
            mViewModel.getTrainings().collect {
                adapter.submitList(it)
            }
        }

        return binding.root
    }

}