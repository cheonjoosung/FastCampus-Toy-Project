package com.example.fastcampus.unit_conversion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.databinding.ActivityUnitConversionBinding


class UnitConversionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnitConversionBinding

    private val list = arrayOf("mm", "cm", "m", "km")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnitConversionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            spinner.adapter = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                arrayOf("mm", "cm", "m", "km")
            )

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    Log.e(localClassName, "${list[i]} 선택")

                    if (binding.etInput.text.toString() == "0" || binding.etInput.text.isNullOrBlank()) {
                        convertUnitInTextValue(binding.tvMm, 0.toDouble())
                        convertUnitInTextValue(binding.tvCm, 0.toDouble())
                        convertUnitInTextValue(binding.tvM, 0.toDouble())
                        convertUnitInTextValue(binding.tvKm, 0.toDouble())
                    } else
                        unitInputChanged(list[i], binding.etInput.text.toString())
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            }

            etInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.e(localClassName, "TextChanged $s")

                    if (binding.etInput.text.toString() == "0" || binding.etInput.text.isNullOrBlank()) {
                        convertUnitInTextValue(binding.tvMm, 0.toDouble())
                        convertUnitInTextValue(binding.tvCm, 0.toDouble())
                        convertUnitInTextValue(binding.tvM, 0.toDouble())
                        convertUnitInTextValue(binding.tvKm, 0.toDouble())
                    } else
                        unitInputChanged(binding.spinner.selectedItem as String, binding.etInput.text.toString())
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }

    /**
     * 1cm -> 10mm, 0.01m, 0.0001km
     */
    private fun unitInputChanged(unit: String, value: String) {
        Log.e(localClassName, "unitInputChanged $unit")

        try {
            val changedValue = value.toDouble()

            when (unit) {
                "mm" -> {
                    val cm = changedValue / 10.toDouble()
                    val m = cm / 100.toDouble()
                    val km = m / 1000.toDouble()

                    Log.e(localClassName, "$unit $changedValue $cm $m $km")
                    convertUnitInTextValue(binding.tvMm, changedValue)
                    convertUnitInTextValue(binding.tvCm, cm)
                    convertUnitInTextValue(binding.tvM, m)
                    convertUnitInTextValue(binding.tvKm, km)
                }

                "cm" -> {
                    val mm = changedValue * 10.toDouble()
                    val m = changedValue / 100.toDouble()
                    val km = m / 1000.toDouble()
                    Log.e(localClassName, "$unit $mm $changedValue $m $km")
                    convertUnitInTextValue(binding.tvMm, mm)
                    convertUnitInTextValue(binding.tvCm, changedValue)
                    convertUnitInTextValue(binding.tvM, m)
                    convertUnitInTextValue(binding.tvKm, km)
                }

                "m" -> {
                    val mm = changedValue / 1000.toDouble()
                    val cm = mm / 10.toDouble()
                    val km = changedValue / 1000.toDouble()
                    Log.e(localClassName, "$unit $mm $cm $changedValue $km")
                    convertUnitInTextValue(binding.tvMm, mm)
                    convertUnitInTextValue(binding.tvCm, cm)
                    convertUnitInTextValue(binding.tvM, changedValue)
                    convertUnitInTextValue(binding.tvKm, km)
                }

                "km" -> {
                    val mm = changedValue / 1000000.toDouble()
                    val cm = mm / 10.toDouble()
                    val m = cm / 100.toDouble()
                    Log.e(localClassName, "$unit $mm $cm $m $changedValue")
                    convertUnitInTextValue(binding.tvMm, mm)
                    convertUnitInTextValue(binding.tvCm, cm)
                    convertUnitInTextValue(binding.tvM, m)
                    convertUnitInTextValue(binding.tvKm, changedValue)
                }
            }
        } catch (e: Exception) {
            Log.e(localClassName, "${e.message}")
        }
    }

    private fun convertUnitInTextValue(tv: TextView, value: Double) {
        try {
            tv.text = String.format("%.8f", value)
        } catch (e: Exception) {
            tv.text = "0"
        }

    }

}