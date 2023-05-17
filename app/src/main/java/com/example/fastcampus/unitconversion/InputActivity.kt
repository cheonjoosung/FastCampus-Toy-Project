package com.example.fastcampus.unitconversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}