package com.example.fastcampus.ch20_youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityYoutubeBinding

class YoutubeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYoutubeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}