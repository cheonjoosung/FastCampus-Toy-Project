package com.example.fastcampus.part1.ch08_music_player

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.MEDIA_PLAYER_PAUSE
import com.example.fastcampus.MEDIA_PLAYER_PLAY
import com.example.fastcampus.MEDIA_PLAYER_STOP
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
        val intent = Intent(this, MediaPlayerService::class.java).apply {
            action = MEDIA_PLAYER_PLAY
        }
        startService(intent)
    }

    private fun stop() {
        val intent = Intent(this, MediaPlayerService::class.java).apply {
            action = MEDIA_PLAYER_STOP
        }
        startService(intent)
    }

    private fun pause() {
        val intent = Intent(this, MediaPlayerService::class.java).apply {
            action = MEDIA_PLAYER_PAUSE
        }
        startService(intent)
    }
}