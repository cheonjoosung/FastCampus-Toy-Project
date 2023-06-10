package com.example.fastcampus.ch18_tomorrow_house

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityTommorowHouseBinding

class TomorrowHouseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTommorowHouseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTommorowHouseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}