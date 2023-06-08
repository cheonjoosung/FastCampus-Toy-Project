package com.example.fastcampus.ch17_genri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityGenriBinding

class GenriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenriBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}