package com.example.fastcampus.ch14_chat.chatlist

data class ChatRoomItem(
    val chatRoomId: String,
    val otherUserName: String,
    val lastMessage: String
)