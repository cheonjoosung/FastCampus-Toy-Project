package com.example.fastcampus.part1.ch01_count_number

import android.os.Bundle
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
                number++
                tvCount.text = number.toString()
            }

            btnReset.setOnClickListener {
                number = 0
                tvCount.text = number.toString()
            }
        }
    }
}