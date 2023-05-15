package com.example.fastcampus.unitconversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityUnitConversionBinding

class UnitConversionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnitConversionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnitConversionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}