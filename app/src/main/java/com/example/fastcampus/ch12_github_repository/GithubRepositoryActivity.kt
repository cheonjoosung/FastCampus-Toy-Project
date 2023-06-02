package com.example.fastcampus.ch12_github_repository

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastcampus.R
import com.example.fastcampus.ch12_github_repository.network.GithubService
import com.example.fastcampus.ch12_github_repository.network.Repo
import com.example.fastcampus.ch12_github_repository.network.UserDto
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

    private lateinit var userAdapter: UserAdapter

    private val handler = Handler(Looper.getMainLooper())
    private var searchFor: String = ""

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter {
            val intent = Intent(this@GithubRepositoryActivity, RepoActivity::class.java)
            intent.putExtra("username", it.username)
            startActivity(intent)
        }

        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        val runnable = Runnable {
            searchUser()
        }

        binding.searchEditText.addTextChangedListener {
            searchFor = it.toString()
            handler.removeCallbacks(runnable)
            handler.postDelayed(
                runnable,
                300
            )
        }
    }

    private fun searchUser() {
        Log.e(localClassName, "searchUser() called")
        val githubService = retrofit.create(GithubService::class.java)

        githubService.searchUsers(query = searchFor).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                Log.e(localClassName, "get search Users success ${response.body().toString()}")
                userAdapter.submitList(response.body()?.items)
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Toast.makeText(this@GithubRepositoryActivity, "Error Occur", Toast.LENGTH_SHORT)
                    .show()
                Log.e(localClassName, "get search Users failed ${t.message}")
            }

        })
    }
}