package com.example.fastcampus.ch0_main

data class Project(
    val name: String,
    val claasName: Class<*>,
    val type: Type
)

enum class Type {
    PART1, PART2, PART3
}
