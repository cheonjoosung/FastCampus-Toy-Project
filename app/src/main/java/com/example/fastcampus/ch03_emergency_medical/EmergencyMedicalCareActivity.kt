package com.example.fastcampus.ch03_emergency_medical

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

    private lateinit var binding: ActivityEmergencyMedicalCareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyMedicalCareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            fabEdit.setOnClickListener {
                Intent(this@EmergencyMedicalCareActivity, InputActivity::class.java).apply {
                    putExtra("intentMessage", "응급 의료 정보")
                    startActivity(this)
                }
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
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("tel:${(tvPhoneValue.text).toString().replace("-", "")}")
                    startActivity(this)
                }
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