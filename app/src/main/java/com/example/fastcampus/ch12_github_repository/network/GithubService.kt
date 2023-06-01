package com.example.fastcampus.ch12_github_repository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{username}/repos")
    fun listRepos(@Path("username") username: String): Call<List<Repo>>
}