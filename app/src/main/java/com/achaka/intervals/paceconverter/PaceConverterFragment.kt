package com.achaka.intervals.paceconverter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentPaceConverterBinding
import kotlin.math.min


class PaceConverterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentPaceConverterBinding.bind(inflater.inflate(R.layout.fragment_pace_converter, container, false))

        //copy result
        val mainField = binding.mainField

        mainField.setOnLongClickListener {
            val clipboardManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", mainField.text)
            clipboardManager.setPrimaryClip(clip)
            return@setOnLongClickListener true
        }
        //here to set dividers : for paces and . for miles, set boundaries, max values
        //count asynchronously values for fields when text changes

        mainField.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //none
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //none
                }

                override fun afterTextChanged(editable: Editable?) {
                    val overall = PaceConverter.minutesStringToSeconds(editable.toString())
                    val milePaceSec = PaceConverter.convertKmPaceToMilePace(overall)
                    binding.firstFieldValue.text = PaceConverter.secondsToMinutesString(milePaceSec.toInt())
                }

            }
        )

        val dynamicUnitsText = binding.units

        dynamicUnitsText.text = getString(R.string.minPerKm)

        dynamicUnitsText.setOnClickListener {
            when(dynamicUnitsText.text) {
                getString(R.string.minPerKm) -> {
                    dynamicUnitsText.text = getString(R.string.minPerMile)
                    binding.firstFieldTitle.text = getString(R.string.minPerKm)
                    binding.secondFieldTitle.text = getString(R.string.kph)
                    binding.thirdFieldTitle.text = getString(R.string.mph)
                    //values
                    binding.firstFieldValue.text
                    binding.secondFieldValue.text
                    binding.thirdFieldValue.text
                    //units
                    return@setOnClickListener
                }
                getString(R.string.minPerMile) -> {
                    //set mph value to
                    //mainField.text
                    dynamicUnitsText.text = getString(R.string.kph)
                    binding.firstFieldTitle.text = getString(R.string.minPerKm)
                    binding.secondFieldTitle.text = getString(R.string.minPerMile)
                    binding.thirdFieldTitle.text = getString(R.string.mph)
                    //values
                    binding.firstFieldValue.text
                    binding.secondFieldValue.text
                    binding.thirdFieldValue.text
                    //units
                    return@setOnClickListener
                }
                getString(R.string.kph) -> {
                    //set mph value to
                    //mainField.text
                    dynamicUnitsText.text = getString(R.string.mph)
                    binding.firstFieldTitle.text = getString(R.string.minPerKm)
                    binding.secondFieldTitle.text = getString(R.string.minPerMile)
                    binding.thirdFieldTitle.text = getString(R.string.kph)
                    //values
                    binding.firstFieldValue.text
                    binding.secondFieldValue.text
                    binding.thirdFieldValue.text
                    //units
                    return@setOnClickListener
                }
                getString(R.string.mph) -> {
                    //set minperkm value to
                    //mainField.text
                    dynamicUnitsText.text = getString(R.string.minPerKm)
                    binding.firstFieldTitle.text = getString(R.string.minPerMile)
                    binding.secondFieldTitle.text = getString(R.string.kph)
                    binding.thirdFieldTitle.text = getString(R.string.mph)
                    //values
                    binding.firstFieldValue.text
                    binding.secondFieldValue.text
                    binding.thirdFieldValue.text
                    //units
                    return@setOnClickListener
                }
            }
        }

        mainField.doAfterTextChanged {
            when (dynamicUnitsText.text) {

            }
            mainField.text.toString()
        }

        return binding.root
    }
}