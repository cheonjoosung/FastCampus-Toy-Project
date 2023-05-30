package com.example.fastcampus.ch11_today_notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityTodayNoticeBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.Socket
import java.net.URL

class TodayNoticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodayNoticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //192.168.209.110
        //192.168.209.164
        Thread {
            try {
                val socket = Socket("192.168.209.110", 8080)
                val printer = PrintWriter(socket.getOutputStream())
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                printer.println("GET / HTTP/1.1")
                printer.println("Host: 192.168.209.110:8080")
                printer.println("User-Agent: android")
                printer.println("\r\n")
                printer.flush()

                var input: String? = "-1"
                while (input != null) {
                    input = reader.readLine()
                    Log.e(localClassName, "Data $input")
                }

                reader.close()
                printer.close()
                socket.close()
            } catch (e: Exception) {
                Log.e(localClassName, "Error $e")
            }
        }.start()
    }
}