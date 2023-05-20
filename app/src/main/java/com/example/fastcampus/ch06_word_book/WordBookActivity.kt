package com.example.fastcampus.ch06_word_book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityWordBookBinding

class WordBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}