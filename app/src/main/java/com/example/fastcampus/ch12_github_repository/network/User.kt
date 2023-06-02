package com.example.fastcampus.ch12_github_repository.network

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val username: String,

    @SerializedName("id")
    val id: Int
)
