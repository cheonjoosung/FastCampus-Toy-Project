package com.example.fastcampus.part1.ch06_word_book

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "word")
data class Word(
    val text: String,
    val mean: String,
    val type: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Parcelable