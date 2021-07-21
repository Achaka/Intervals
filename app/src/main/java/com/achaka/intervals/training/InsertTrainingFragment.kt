package com.achaka.intervals.training

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.achaka.intervals.IntervalsApp
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentInsertTrainingBinding

class InsertTrainingFragment : Fragment() {

    private val mViewModel: TrainingsViewModel by activityViewModels {
        TrainingsViewModelFactory(
            (activity?.application as IntervalsApp).database.trainingDao()
        )
    }
    private lateinit var binding: FragmentInsertTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInsertTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun insertTraining(training: Training) {
        mViewModel.insertTraining(training)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.insert_training_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.submit_training -> {
                insertTraining(Training(0, binding.trainingName.text.toString()))
                val action = InsertTrainingFragmentDirections.actionInsertTrainingFragmentToIntervalsFragment()
                view?.findNavController()?.navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}