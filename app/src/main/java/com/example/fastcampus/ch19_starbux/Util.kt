package com.example.fastcampus.ch19_starbux

import android.content.Context
import com.google.gson.Gson

fun Context.readData(): Home? {

    return try {
        val inputStream = this.resources.assets.open("home.json")
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        val gson = Gson()
        gson.fromJson(String(buffer), Home::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}