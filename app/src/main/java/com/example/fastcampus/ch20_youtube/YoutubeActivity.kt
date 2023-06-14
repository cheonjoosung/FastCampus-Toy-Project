package com.example.fastcampus.ch20_youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityYoutubeBinding

class YoutubeActivity : AppCompatActivity() {

    private val binding: ActivityYoutubeBinding by lazy {
        ActivityYoutubeBinding.inflate(layoutInflater)
    }

    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        videoAdapter = VideoAdapter(context = this)

        with(binding) {
            videoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = videoAdapter
            }
        }

        val videoList = readData("video.json", VideoList::class.java) ?: VideoList(emptyList())
        Log.e(localClassName, "videoList ${videoList.videos.size}")
        videoAdapter.submitList(videoList.videos)
    }
}