package com.example.fastcampus.part2.ch20_youtube.player

data class PlayerHeader(
    override val id: String,
    val title: String,
    val channelName: String,
    val viewCount: String,
    val dateText: String,
    val channelThumb: String,
): PlayerVideoModel