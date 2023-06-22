package com.example.fastcampus.part1.ch01_count_number

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.databinding.ActivityCountNumberBinding

class CountNumberActivity : AppCompatActivity() {

    private val binding: ActivityCountNumberBinding by lazy {
        ActivityCountNumberBinding.inflate(layoutInflater)
    }

    private var number = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {

            addButton.setOnClickListener {
                number++
                countTextView.text = number.toString()
            }

            resetButton.setOnClickListener {
                number = 0
                countTextView.text = number.toString()
            }
        }
    }
}