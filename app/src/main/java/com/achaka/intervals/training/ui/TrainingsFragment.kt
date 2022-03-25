package com.achaka.intervals.training.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.achaka.intervals.IntervalsApp
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentTrainingsBinding
import com.achaka.intervals.training.model.TrainingRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class TrainingsFragment : Fragment() {

    private val subscriptions = CompositeDisposable()

    @Inject
    lateinit var repository: TrainingRepository

    private var _binding: FragmentTrainingsBinding? = null
    private val binding get() = _binding!!

//    private val mViewModel: TrainingsViewModel by viewModels {
//        TrainingsViewModelFactory(
//            (activity?.application as IntervalsApp).database.trainingDao()
//        )
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        (requireActivity().applicationContext as IntervalsApp).trainingComponent.inject(this)
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
            val action = TrainingsFragmentDirections.actionTrainingsFragmentToIntervalsFragment(
                trainingId = it.id
            )
            view?.findNavController()?.navigate(action)
        }, {
//            val deleteSub = mViewModel.deleteTraining(it)
//                .subscribeOn(Schedulers.io())
//                .subscribe({
//
//                }, {
//                    Toast.makeText(this.context, getString(R.string.unknownError), Toast.LENGTH_SHORT).show()
//                })
//            subscriptions.add(deleteSub)
        }
        )

        binding.addTrainingFab.setImageResource(R.drawable.ic_sharp_add_24)

        binding.addTrainingFab.setOnClickListener {
            val action =
                TrainingsFragmentDirections.actionTrainingsFragmentToInsertTrainingFragment()
            view?.findNavController()?.navigate(action)
        }

        recyclerView.adapter = adapter
        val trainingsObserver = repository.getTrainings().subscribeOn(Schedulers.newThread())
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

//        val trainingsObserver = mViewModel.getTrainings()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    adapter.submitList(it)
//                },
//                {
//                    Toast.makeText(this.context, getString(R.string.trainingsLoadError), Toast.LENGTH_SHORT).show()
//                }
//            )

        subscriptions.add(trainingsObserver)
    }

    override fun onDestroyView() {
        subscriptions.clear()
        super.onDestroyView()
    }
}