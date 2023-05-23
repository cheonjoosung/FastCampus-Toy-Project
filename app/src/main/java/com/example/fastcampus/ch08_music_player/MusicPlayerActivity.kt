package com.example.fastcampus.ch08_music_player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.databinding.ActivityMusicPlayerBinding

class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}