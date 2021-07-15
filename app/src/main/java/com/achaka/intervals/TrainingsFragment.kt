package com.achaka.intervals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.intervals.databinding.FragmentTrainingsBinding
import com.achaka.intervals.databinding.TrainingItemBinding


class TrainingsFragment : Fragment() {

    private val viewModel: IntervalsSharedViewModel by activityViewModels {
        IntervalsSharedViewModelFactory(
            (activity?.application as IntervalsApp).database.intervalDao(),
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



        recyclerView.adapter = adapter
        adapter.submitList(viewModel.getTestTrainings())



        return binding.root
    }

}