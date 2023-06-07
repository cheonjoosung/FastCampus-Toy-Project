package com.example.fastcampus.ch15_weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}