package com.example.fastcampus.part2.ch20_youtube

import com.google.gson.annotations.SerializedName

data class VideoList(
    @SerializedName("videos")
    val videos: List<VideoEntity>
)

data class VideoEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("sources")
    val videoUrl: String,
    @SerializedName("channelName")
    val channelName: String,
    @SerializedName("viewCount")
    val viewCount: String,
    @SerializedName("dateText")
    val dateText: String,
    @SerializedName("channelThumb")
    val channelThumb: String,
    @SerializedName("thumb")
    val videoThumb: String,
)


/**
"id": "a",
"description": "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.\n\nLicensed under the Creative Commons Attribution license\nhttps://www.bigbuckbunny.org",
"sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
"channelName": "Blender Foundation",
"viewCount": "24만회",
"dateText": "6개월 전",
"channelThumb": "https://picsum.photos/seed/Blender/40",
"thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
"title":
 */