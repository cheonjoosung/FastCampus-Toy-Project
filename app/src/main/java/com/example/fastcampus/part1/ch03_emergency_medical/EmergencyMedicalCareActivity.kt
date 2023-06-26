package com.example.fastcampus.part1.ch03_emergency_medical

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.BIRTH
import com.example.fastcampus.BLOOD
import com.example.fastcampus.ETC
import com.example.fastcampus.NAME
import com.example.fastcampus.PHONE
import com.example.fastcampus.USER_INFORMATION
import com.example.fastcampus.databinding.ActivityEmergencyMedicalCareBinding

class EmergencyMedicalCareActivity : AppCompatActivity() {

    private val binding: ActivityEmergencyMedicalCareBinding by lazy {
        ActivityEmergencyMedicalCareBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {

            fabEdit.setOnClickListener {
                startActivity(
                    Intent(this@EmergencyMedicalCareActivity, InputActivity::class.java)
                )
            }

            fabDelete.setOnClickListener {
                getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit().apply {
                    clear()
                    apply()
                }

                updateUI()
                Toast.makeText(applicationContext, "지움!!", Toast.LENGTH_SHORT).show()
            }

            tvPhoneValue.setOnClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("tel:${(tvPhoneValue.text).toString().replace("-", "")}")
                    }
                )
            }
        }

    }

    private fun updateUI() {
        getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).apply {
            binding.tvNameValue.text = getString(NAME, "--")
            binding.tvBloodValue.text = getString(BLOOD, "--")
            binding.tvPhoneValue.text = getString(PHONE, "--")
            binding.tvBirthValue.text = getString(BIRTH, "--")
            binding.tvEtcValue.text = getString(ETC, "--")
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }
}