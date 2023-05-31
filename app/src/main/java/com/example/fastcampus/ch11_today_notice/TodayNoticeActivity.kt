package com.example.fastcampus.ch11_today_notice

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.fastcampus.databinding.ActivityTodayNoticeBinding
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class TodayNoticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodayNoticeBinding

    private val serverURL = "http://192.168.209.110:8080"

    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            confirmButton.setOnClickListener {
                /**
                 * socket 통신은 low level 이라 http 가 가능하지만
                 * okhttp 통신의 경우는 http 보안 문제로 인해 기본적으로 block 됨
                 *  java.net.UnknownServiceException: CLEARTEXT communication to 192.168.209.110 not permitted by network security policy
                 * Manifest 에서 android:usesCleartextTraffic="true" 추가해주면 테스트 가능해짐
                 */

                val request: Request = Request.Builder()
                    .url(serverURL)
                    .build()

                val callback = object : Callback {

                    override fun onFailure(call: Call, e: IOException) {
                        Log.e(localClassName, "Failed $e")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            val response = response.body?.string()
                            val message = Gson().fromJson(response, Message::class.java)
                            runOnUiThread {
                                noticeTextView.text = message.message + "\n by " + message.nickname
                                noticeTextView.visibility = View.VISIBLE
                                serverHostEditText.isVisible = false
                                confirmButton.isVisible = false
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    applicationContext,
                                    "Network Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                // execute()는 동기로 black 발생하기에 main thread 에서 사용하면 안 됨
                client.newCall(request).enqueue(callback)
            }


        }

    }
}


/*** Socket Code
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
 */