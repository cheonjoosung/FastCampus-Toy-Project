package com.example.fastcampus.ch12_github_repository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @Headers("Authorization: Bearer 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")
    @GET("users/{username}/repos")
    fun listRepos(@Path("username") username: String): Call<List<Repo>>

    @Headers("Authorization: Bearer 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")
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<UserDto>
}