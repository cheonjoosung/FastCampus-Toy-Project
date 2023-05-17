package com.example.fastcampus.emergencymedical

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    private val list = listOf("A", "B", "AB", "O")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra("intentMessage") ?: "없음"

        with(binding) {

            spinnerBlood.adapter = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                list
            )

            spinnerBlood.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

            birthDateLayer.setOnClickListener {
                val listener = OnDateSetListener { date, year, month, dayOfMonth ->
                    Log.e(localClassName, "??? $year-${month.inc()}-$dayOfMonth")
                    tvBirthValue.text = "$year-${month.inc()}-$dayOfMonth"
                }

                DatePickerDialog(this@InputActivity, listener, 2000, 2, 1).show()
            }
        }
    }
}