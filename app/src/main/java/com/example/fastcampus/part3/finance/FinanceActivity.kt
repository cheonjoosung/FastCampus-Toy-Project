package com.example.fastcampus.part3.finance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityFinanceBinding

class FinanceActivity : AppCompatActivity() {

    private val binding: ActivityFinanceBinding by lazy {
        ActivityFinanceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}