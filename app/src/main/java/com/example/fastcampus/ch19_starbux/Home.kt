package com.example.fastcampus.ch19_starbux

data class Home(
    val user: User,
    val appbarImage: String
)

data class User(
    val nickname: String,
    val starCount: Int,
    val totalCount: Int
)