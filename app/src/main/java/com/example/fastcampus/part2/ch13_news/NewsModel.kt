package com.example.fastcampus.part2.ch13_news

import com.example.fastcampus.part2.ch13_news.network.NewsItem

data class NewsModel(
    val title: String,
    val link: String,
    var imageUrl: String? = null,
)

fun List<NewsItem>.transform(): List<NewsModel> {
    return this.map {
        NewsModel(
            title = it.title ?: "",
            link = it.link ?: "",
            imageUrl = null
        )
    }
}