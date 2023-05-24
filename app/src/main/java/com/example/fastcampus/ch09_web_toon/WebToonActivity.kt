package com.example.fastcampus.ch09_web_toon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityWebToonBinding

class WebToonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebToonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebToonBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}