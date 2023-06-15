package com.example.fastcampus.part2.ch12_github_repository

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityRepoBinding
import com.example.fastcampus.part2.ch12_github_repository.network.ApiClient
import com.example.fastcampus.part2.ch12_github_repository.network.GithubService
import com.example.fastcampus.part2.ch12_github_repository.network.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoBinding

    private lateinit var repoAdapter: RepoAdapter

    private var page = 0

    private var hasMore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val username = intent.getStringExtra("username") ?: return
        binding.usernameTextView.text = username

        repoAdapter = RepoAdapter {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl))
            startActivity(intent)
        }
        val linearLayoutManager = LinearLayoutManager(this@RepoActivity)

        binding.repoRecyclerView.apply {
            adapter = repoAdapter
            layoutManager = linearLayoutManager
        }

        binding.repoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalCount = linearLayoutManager.itemCount
                val lastVisitPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisitPosition >= totalCount - 1 && hasMore) {
                    page += 1
                    Toast.makeText(this@RepoActivity, getString(R.string.msg_repo_more), Toast.LENGTH_SHORT).show()
                    listRepo(username, page)
                }
            }
        })

        listRepo(username, 0)
    }

    private fun listRepo(username: String, page: Int) {
        val githubService = ApiClient.retrofit.create(GithubService::class.java)

        githubService.listRepos(username = username, page = page)
            .enqueue(object : Callback<List<Repo>> {
                override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                    Log.e(localClassName, "get repo success ${response.body().toString()}")
                    hasMore = response.body()?.count() == 30
                    repoAdapter.submitList(repoAdapter.currentList + response.body().orEmpty())
                }

                override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                    Log.e(localClassName, "get repo failed ${t.message}")
                }

            })
    }
}