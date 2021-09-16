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
import androidx.lifecycle.lifecycleScope
import com.achaka.intervals.R
import com.achaka.intervals.databinding.FragmentPaceConverterBinding
import com.achaka.intervals.paceconverter.PaceConverter.Companion.calculateInKmPace
import com.achaka.intervals.paceconverter.PaceConverter.Companion.calculateInKph
import com.achaka.intervals.paceconverter.PaceConverter.Companion.calculateInMilePace
import com.achaka.intervals.paceconverter.PaceConverter.Companion.calculateInMph
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.min


class PaceConverterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentPaceConverterBinding.bind(inflater.inflate(R.layout.fragment_pace_converter, container, false))

        //copy result
        val mainField = binding.mainField
        val dynamicUnitsText = binding.units

        mainField.setOnLongClickListener {
            val clipboardManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", mainField.text)
            clipboardManager.setPrimaryClip(clip)
            return@setOnLongClickListener true
        }
        //here to set dividers : for paces and . for miles, set boundaries, max values
        //count asynchronously values for fields when text changes

        //pre-populate the main field
        dynamicUnitsText.text = getString(R.string.minPerKm)
        mainField.setText(SpannableStringBuilder.valueOf(":"))
        binding.firstFieldTitle.text = getString(R.string.minPerMile)
        binding.secondFieldTitle.text = getString(R.string.kph)
        binding.thirdFieldTitle.text = getString(R.string.mph)

        val tv = object: TextWatcher {

            override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) {
                //length before change
                Log.d("length string before", string?.length.toString())
                //after is 0 when text added
                Log.d("after", after.toString())
            }

            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                //none
                //on string is actual length
                Log.d("length string on", string?.length.toString())
                //before is 0 when text added
                Log.d("before", before.toString())
            }

            override fun afterTextChanged(editable: Editable?) {
                val results = mutableListOf<String>()
                when (dynamicUnitsText.text.toString()) {
                    getString(R.string.minPerKm) -> {
                        results.addAll(calculateInKmPace(editable.toString()))
                    }
                    getString(R.string.minPerMile) -> {
                        results.addAll(calculateInMilePace(editable.toString()))
                    }
                    getString(R.string.kph) -> {
                        results.addAll(calculateInKph(editable.toString()))
                    }
                    getString(R.string.mph) -> {
                        results.addAll(calculateInMph(editable.toString()))
                    }
                }
                binding.firstFieldValue.text = results[0]
                binding.secondFieldValue.text = results[1]
                binding.thirdFieldValue.text = results[2]
            }
        }

        mainField.addTextChangedListener(
            tv
        )

        dynamicUnitsText.setOnClickListener {
            when(dynamicUnitsText.text) {
                getString(R.string.minPerKm) -> {
                    //take existing value in minpermile and load into mainfield
                    if (binding.firstFieldValue.text.isNotEmpty()) {
                        val minMileResult = binding.firstFieldValue.text
                        mainField.removeTextChangedListener(tv)
                        mainField.text = SpannableStringBuilder.valueOf(minMileResult)
                        val results = calculateInMilePace(minMileResult.toString())
                        binding.firstFieldValue.text = results[0]
                        binding.secondFieldValue.text = results[1]
                        binding.thirdFieldValue.text = results[2]
                        mainField.addTextChangedListener(tv)
                    }
                    dynamicUnitsText.text = getString(R.string.minPerMile)
                    binding.firstFieldTitle.text = getString(R.string.minPerKm)
                    binding.secondFieldTitle.text = getString(R.string.kph)
                    binding.thirdFieldTitle.text = getString(R.string.mph)
                    //units
                    return@setOnClickListener
                }
                getString(R.string.minPerMile) -> {
                    //set mph value to
                    //mainField.text
                    if (binding.firstFieldValue.text.isNotEmpty()) {
                        val minMileResult = binding.secondFieldValue.text
                        mainField.removeTextChangedListener(tv)
                        mainField.text = SpannableStringBuilder.valueOf(minMileResult)
                        val results = calculateInKph(minMileResult.toString())
                        binding.firstFieldValue.text = results[0]
                        binding.secondFieldValue.text = results[1]
                        binding.thirdFieldValue.text = results[2]
                        mainField.addTextChangedListener(tv)
                    }
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
                    if (binding.firstFieldValue.text.isNotEmpty()) {
                        val minMileResult = binding.thirdFieldValue.text
                        mainField.removeTextChangedListener(tv)
                        mainField.text = SpannableStringBuilder.valueOf(minMileResult)
                        val results = calculateInMph(minMileResult.toString())
                        binding.firstFieldValue.text = results[0]
                        binding.secondFieldValue.text = results[1]
                        binding.thirdFieldValue.text = results[2]
                        mainField.addTextChangedListener(tv)
                    }
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
                    if (binding.firstFieldValue.text.isNotEmpty()) {
                        val minMileResult = binding.firstFieldValue.text
                        mainField.removeTextChangedListener(tv)
                        mainField.text = SpannableStringBuilder.valueOf(minMileResult)
                        val results = calculateInKmPace(minMileResult.toString())
                        binding.firstFieldValue.text = results[0]
                        binding.secondFieldValue.text = results[1]
                        binding.thirdFieldValue.text = results[2]
                        mainField.addTextChangedListener(tv)
                    }
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

        return binding.root
    }


}