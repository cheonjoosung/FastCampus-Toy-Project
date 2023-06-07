package com.example.fastcampus.ch16_map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}