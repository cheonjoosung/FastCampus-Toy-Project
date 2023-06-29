package com.example.fastcampus.ch0_main

import com.example.fastcampus.R

data class Project(
    val name: String,
    val claasName: Class<*>,
    val type: Type,
    val imageResInt: Int? = R.drawable.baseline_android_24
)

enum class Type {
    PART1, PART2, PART3
}
