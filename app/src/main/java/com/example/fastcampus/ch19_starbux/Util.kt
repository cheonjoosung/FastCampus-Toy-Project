package com.example.fastcampus.ch19_starbux

import android.content.Context
import com.google.gson.Gson

fun <T> Context.readData(filename: String, classT: Class<T>): T? {

    return try {
        val inputStream = this.resources.assets.open(filename)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        val gson = Gson()
        gson.fromJson(String(buffer), classT)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}