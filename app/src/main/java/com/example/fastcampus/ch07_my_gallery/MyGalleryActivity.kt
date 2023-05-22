package com.example.fastcampus.ch07_my_gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityMyGalleryBinding

class MyGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}