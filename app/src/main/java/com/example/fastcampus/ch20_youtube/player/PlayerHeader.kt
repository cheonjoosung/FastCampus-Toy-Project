package com.example.fastcampus.ch20_youtube.player

data class PlayerHeader(
    override val id: String,
    val title: String,
    val channelName: String,
    val viewCount: String,
    val dateText: String,
    val channelThumb: String,
): PlayerVideoModel