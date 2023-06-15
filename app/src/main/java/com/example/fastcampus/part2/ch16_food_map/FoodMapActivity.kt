package com.example.fastcampus.part2.ch16_food_map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.databinding.ActivityFoodMapBinding

class FoodMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}