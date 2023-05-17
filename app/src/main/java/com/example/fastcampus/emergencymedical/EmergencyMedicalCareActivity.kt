package com.example.fastcampus.emergencymedical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityEmergencyMedicalCareBinding

class EmergencyMedicalCareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmergencyMedicalCareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyMedicalCareBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}