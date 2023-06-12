package com.example.fastcampus.ch18_tomorrow_house

data class ArticleModel(
    val articleId: String ?= null,
    val createdAt: Long ?= null,
    val description: String ?= null,
    val imageUrl: String? = null
)