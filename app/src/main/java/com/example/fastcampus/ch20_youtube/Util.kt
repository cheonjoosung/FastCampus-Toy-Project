package com.example.fastcampus.ch20_youtube

import android.content.Context
import android.util.Log
import com.google.gson.Gson

fun <T> Context.readData(fileName: String, classT: Class<T>): T? {
    return try {
        val inputStream = this.resources.assets.open(fileName)
        val byteArray = ByteArray(inputStream.available())
        inputStream.read(byteArray)
        inputStream.close()

        Gson().fromJson(String(byteArray), classT)
    } catch (e: Exception) {
        null
    }
}