package com.example.fastcampus.ch12_github_repository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fastcampus.R
import com.example.fastcampus.ch12_github_repository.network.GithubService
import com.example.fastcampus.ch12_github_repository.network.Repo
import com.example.fastcampus.databinding.ActivityGithubRepositoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GithubRepositoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGithubRepositoryBinding

    private val url = "https://api.github.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val githubService = retrofit.create(GithubService::class.java)
        githubService.listRepos(username = "cheonjoosung").enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e(localClassName, "get repo success ${response.body().toString()}")
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Log.e(localClassName, "get repo failed ${t.message}")
            }

        })
    }
}