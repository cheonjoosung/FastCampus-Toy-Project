package com.example.fastcampus.ch14_chat.chatlist

data class ChatRoomItem(
    val chatRoomId: String ?= null,
    val otherUserName: String ?= null,
    val lastMessage: String ?= null,
    val otherUserId: String ?= null
)