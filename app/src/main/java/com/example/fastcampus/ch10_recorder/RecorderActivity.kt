package com.example.fastcampus.ch10_recorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityRecorderBinding

class RecorderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecorderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecorderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}