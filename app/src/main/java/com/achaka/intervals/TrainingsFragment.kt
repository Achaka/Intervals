package com.achaka.intervals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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

        val adapter = TrainingsAdapter {}
        recyclerView.adapter = adapter
        adapter.submitList(viewModel.getTestTrainings())

        return binding.root
    }

}