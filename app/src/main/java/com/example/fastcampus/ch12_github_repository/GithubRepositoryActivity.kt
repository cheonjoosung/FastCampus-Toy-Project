package com.example.fastcampus.ch12_github_repository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityGithubRepositoryBinding

class GithubRepositoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGithubRepositoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}