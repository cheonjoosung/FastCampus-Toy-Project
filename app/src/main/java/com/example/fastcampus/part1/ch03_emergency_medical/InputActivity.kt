package com.example.fastcampus.part1.ch03_emergency_medical

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.BIRTH
import com.example.fastcampus.BLOOD
import com.example.fastcampus.ETC
import com.example.fastcampus.NAME
import com.example.fastcampus.PHONE
import com.example.fastcampus.USER_INFORMATION
import com.example.fastcampus.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    private val list = listOf("A", "B", "AB", "O")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            spinnerBlood.apply {

                adapter = ArrayAdapter(
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
                        /** do nothing **/
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        /** do nothing **/
                    }
                }
            }

            birthDateLayer.setOnClickListener {
                val listener = OnDateSetListener { _, year, month, dayOfMonth ->
                    val str = "$year-${month.inc()}-$dayOfMonth"
                    tvBirthValue.text = str
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