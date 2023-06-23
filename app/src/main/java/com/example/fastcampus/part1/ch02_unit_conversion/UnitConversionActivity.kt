package com.example.fastcampus.part1.ch02_unit_conversion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityUnitConversionBinding


class UnitConversionActivity : AppCompatActivity() {

    private val binding: ActivityUnitConversionBinding by lazy {
        ActivityUnitConversionBinding.inflate(layoutInflater)
    }

    private val list = arrayOf("mm", "cm", "m", "km")

    private val inputSpinnerAdapter by lazy {
        ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            arrayOf("mm", "cm", "m", "km")
        )
    }

    private val outputSpinnerAdapter by lazy {
        ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            arrayOf("mm", "cm", "m", "km")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setInputSpinner()
        setOutputSpinner()
        setEditInput()
    }

    private fun setInputSpinner() {
        with(binding) {
            inputSpinner.apply {
                adapter = inputSpinnerAdapter

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        adapterView: AdapterView<*>,
                        view: View,
                        i: Int,
                        l: Long
                    ) {
                        if (inputEditText.text.toString() == "0" || inputEditText.text.isNullOrBlank()) {
                            inputEditText.text.clear()
                            outPutTextView.text = ""
                        } else
                            unitInputChanged(
                                list[i],
                                outputSpinner.selectedItem as String,
                                inputEditText.text.toString()
                            )
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>) {}
                }
            }
        }
    }

    private fun setOutputSpinner() {
        with(binding) {
            outputSpinner.apply {
                adapter = outputSpinnerAdapter

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        adapterView: AdapterView<*>,
                        view: View,
                        i: Int,
                        l: Long
                    ) {
                        if (inputEditText.text.toString() == "0" || inputEditText.text.isNullOrBlank()) {
                            inputEditText.text.clear()
                            outPutTextView.text = ""
                        } else {
                            unitInputChanged(
                                inputSpinner.selectedItem as String,
                                list[i],
                                inputEditText.text.toString()
                            )
                        }
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>) {}
                }
            }
        }
    }

    private fun setEditInput() {
        with(binding) {
            inputEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    /** do nothing **/
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if (inputEditText.text.toString() == "0" || inputEditText.text.isNullOrBlank()) {
                        inputEditText.text.clear()
                        outPutTextView.text = ""
                    } else
                        unitInputChanged(
                            inputSpinner.selectedItem as String,
                            outputSpinner.selectedItem as String,
                            inputEditText.text.toString()
                        )
                }

                override fun afterTextChanged(s: Editable?) {
                    /** do nothing **/
                }
            })
        }
    }

    /**
     * 1cm -> 10mm, 0.01m, 0.0001km
     */
    private fun unitInputChanged(inputUnit: String, outputUnit: String, value: String) {

        try {
            val changedValue = value.toDouble()

            when (inputUnit) {
                "mm" -> {
                    val cm = changedValue / 10.toDouble()
                    val m = cm / 100.toDouble()
                    val km = m / 1000.toDouble()

                    val result = when (outputUnit) {
                        "mm" -> changedValue
                        "cm" -> cm
                        "m" -> m
                        else -> km
                    }

                    convertUnitInTextValue(binding.outPutTextView, result)
                }

                "cm" -> {
                    val mm = changedValue * 10.toDouble()
                    val m = changedValue / 100.toDouble()
                    val km = m / 1000.toDouble()

                    val result = when (outputUnit) {
                        "mm" -> mm
                        "cm" -> changedValue
                        "m" -> m
                        else -> km
                    }

                    convertUnitInTextValue(binding.outPutTextView, result)
                }

                "m" -> {
                    val mm = changedValue / 1000.toDouble()
                    val cm = mm / 10.toDouble()
                    val km = changedValue / 1000.toDouble()

                    val result = when (outputUnit) {
                        "mm" -> mm
                        "cm" -> cm
                        "m" -> changedValue
                        else -> km
                    }

                    convertUnitInTextValue(binding.outPutTextView, result)
                }

                "km" -> {
                    val mm = changedValue / 1000000.toDouble()
                    val cm = mm / 10.toDouble()
                    val m = cm / 100.toDouble()

                    val result = when (outputUnit) {
                        "mm" -> mm
                        "cm" -> cm
                        "m" -> m
                        else -> changedValue
                    }

                    convertUnitInTextValue(binding.outPutTextView, result)
                }
            }
        } catch (e: Exception) {
            convertUnitInTextValue(binding.outPutTextView, 0.toDouble())
            convertUnitInTextValue(binding.inputEditText, 0.toDouble())
        }
    }

    private fun convertUnitInTextValue(tv: TextView, value: Double) {
        try {
            tv.text = String.format("%.8f", value)
        } catch (e: Exception) {
            tv.text = getString(R.string.zero)
        }

    }

}