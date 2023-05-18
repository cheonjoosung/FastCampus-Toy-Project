package com.example.fastcampus.ch01_count_number

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.databinding.ActivityCountNumberBinding

class CountNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountNumberBinding

    private var number = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            btnAdd.setOnClickListener {
                Log.d(localClassName, "Button Add Clicked")

                number++
                tvCount.text = number.toString()
            }

            btnReset.setOnClickListener {
                Log.d(localClassName, "Button Reset Clicked")

                number = 0
                tvCount.text = number.toString()
            }
        }
    }
}