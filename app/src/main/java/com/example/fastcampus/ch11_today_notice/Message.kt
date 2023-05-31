package com.example.fastcampus.ch11_today_notice

import com.google.gson.annotations.SerializedName


data class Message(
    // @SerializedName("message") 받고 변수값을 바꿔서 디컴파일시 노출 방지 난독화
    val message: String,
    val nickname: String
)