package com.example.fastcampus.ch08_music_player

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityMusicPlayerBinding

class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicPlayerBinding

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener { play() }
        binding.stopButton.setOnClickListener { stop() }
        binding.pauseButton.setOnClickListener { pause() }
    }

    private fun play() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.electronic).apply {
                isLooping = true
            }
        }

        mediaPlayer?.start()
    }

    private fun stop() {
        mediaPlayer?.stop()
    }

    private fun pause() {
        mediaPlayer?.pause()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}