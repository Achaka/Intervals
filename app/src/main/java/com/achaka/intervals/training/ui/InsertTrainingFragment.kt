package com.achaka.intervals.training.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentInsertTrainingBinding
import com.achaka.intervals.interval.model.IntervalFragmentMode


class InsertTrainingFragment : Fragment() {

    private lateinit var binding: FragmentInsertTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInsertTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.insert_training_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.submit_training -> {
                val trainingName = binding.trainingName.text.toString()
                if (trainingName.isEmpty()) {
                    Toast.makeText(this.requireContext(), getString(R.string.trainingEmptyNameError), Toast.LENGTH_SHORT).show()
                    return false
                }
                val action = InsertTrainingFragmentDirections
                    .actionInsertTrainingFragmentToIntervalsFragment(
                        IntervalFragmentMode.NEW_TRAINING_EDIT_MODE,
                        trainingName
                    )
                view?.findNavController()?.navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}