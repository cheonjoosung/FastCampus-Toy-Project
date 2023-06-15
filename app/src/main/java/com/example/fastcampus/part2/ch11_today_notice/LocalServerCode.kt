package com.example.fastcampus.part2.ch11_today_notice

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {

    //192.168.209.110
    //192.168.209.164

    Thread {
        val port = 8080
        val server = ServerSocket(port)

        while (true) {
            val socket = server.accept()

            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var input: String? = "-1"

            while (input != null && input != "") {
                input = reader.readLine()
            }
            println("DATA $input")

            printer.println("HTTP/1.1 200 OK")
            printer.println("Content-Type: text/html\r\n")
            // Body
            printer.println("{\"message\": \"hello world\", \"nickname\": \"홍길동\"}")
            printer.println("\r\n")
            printer.flush()

            reader.close()
            socket.close()
        }
    }.start()
}