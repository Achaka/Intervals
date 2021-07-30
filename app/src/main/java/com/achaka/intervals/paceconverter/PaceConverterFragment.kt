package com.achaka.intervals.paceconverter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentPaceConverterBinding


class PaceConverterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentPaceConverterBinding.bind(inflater
                .inflate(R.layout.fragment_pace_converter, container, false))



        //copy result
        val mainField = binding.textInputEditText
        mainField.setOnLongClickListener {
            val clipboardManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", mainField.text)
            clipboardManager.setPrimaryClip(clip)
            return@setOnLongClickListener true
        }
        //here to set dividers : for paces and . for miles, set boundaries, max values
        //count asynchronously values for fields when text changes
        mainField.doBeforeTextChanged { text, start, count, after ->  }
        mainField.doOnTextChanged { text, start, before, count ->  }
        mainField.doAfterTextChanged {  }

        //change units
        val dynamicUnitsText = binding.units
        dynamicUnitsText.setOnClickListener {
            when (dynamicUnitsText.text) {
                getString(R.string.minPerKm) -> {
                    //values
                    binding.firstFieldValue.text
                    binding.secondFieldValue.text
                    binding.thirdFieldValue.text
                    //units
                    binding.firstFieldTitle.text = getString(R.string.minPerMile)
                    binding.secondFieldTitle.text = getString(R.string.kph)
                    binding.thirdFieldTitle.text = getString(R.string.mph)
                }
                getString(R.string.minPerMile) -> {
                    //values
                    binding.firstFieldValue.text
                    binding.secondFieldValue.text
                    binding.thirdFieldValue.text
                    //units
                    binding.firstFieldTitle.text = getString(R.string.minPerKm)
                    binding.secondFieldTitle.text = getString(R.string.kph)
                    binding.thirdFieldTitle.text = getString(R.string.mph)
                }
                getString(R.string.kph) -> {
                    //values
                    binding.firstFieldValue.text
                    binding.secondFieldValue.text
                    binding.thirdFieldValue.text
                    //units
                    binding.firstFieldTitle.text = getString(R.string.minPerKm)
                    binding.secondFieldTitle.text = getString(R.string.minPerMile)
                    binding.thirdFieldTitle.text = getString(R.string.mph)
                }
                getString(R.string.mph) -> {
                    //values
                    binding.firstFieldValue.text
                    binding.secondFieldValue.text
                    binding.thirdFieldValue.text
                    //units
                    binding.firstFieldTitle.text = getString(R.string.minPerKm)
                    binding.secondFieldTitle.text = getString(R.string.minPerMile)
                    binding.thirdFieldTitle.text = getString(R.string.kph)
                }
            }
        }

        return inflater.inflate(R.layout.fragment_pace_converter, container, false)
    }
}