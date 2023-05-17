package com.example.fastcampus.emergencymedical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.databinding.ActivityEmergencyMedicalCareBinding

class EmergencyMedicalCareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmergencyMedicalCareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyMedicalCareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with (binding) {

            floatingActionButton.setOnClickListener {
                Intent(this@EmergencyMedicalCareActivity, InputActivity::class.java).apply {
                    putExtra("intentMessage", "응급 의료 정보")
                    startActivity(this)
                }
            }
        }
    }
}