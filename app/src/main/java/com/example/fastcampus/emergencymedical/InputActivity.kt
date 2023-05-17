package com.example.fastcampus.emergencymedical

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.fastcampus.BIRTH
import com.example.fastcampus.BLOOD
import com.example.fastcampus.ETC
import com.example.fastcampus.NAME
import com.example.fastcampus.PHONE
import com.example.fastcampus.R
import com.example.fastcampus.USER_INFORMATION
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

            btnSave.setOnClickListener {

                val blood =
                    if (binding.rbRgPlus.isSelected) "rh+" else "rh-" + spinnerBlood.selectedItem.toString()

                getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit().apply {
                    putString(NAME, etNameValue.text.toString())
                    putString(BLOOD, blood)
                    putString(PHONE, etPhoneValue.text.toString())
                    putString(BIRTH, tvBirthValue.text.toString())
                    putString(ETC, etEtcValue.text.toString())

                    apply() //async (다른 쓰레드를 열어서 동작)
                    Toast.makeText(applicationContext, "저장!!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}