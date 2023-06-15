package com.example.fastcampus.part2.ch14_chat.chatlist

data class ChatRoomItem(
    val chatRoomId: String ?= null,
    val otherUserName: String ?= null,
    val lastMessage: String ?= null,
    val otherUserId: String ?= null
)