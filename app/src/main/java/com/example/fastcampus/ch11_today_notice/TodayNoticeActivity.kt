package com.example.fastcampus.ch11_today_notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityTodayNoticeBinding

class TodayNoticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodayNoticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}