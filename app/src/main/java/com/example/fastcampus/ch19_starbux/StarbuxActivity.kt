package com.example.fastcampus.ch19_starbux

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.databinding.ActivityStartbuxBinding

class StarbuxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartbuxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartbuxBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}