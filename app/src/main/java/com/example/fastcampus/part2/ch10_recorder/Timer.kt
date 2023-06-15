package com.example.fastcampus.part2.ch10_recorder

import android.os.Handler
import android.os.Looper

class Timer(
    listener: OnTimerTickListener
) {

    private var duration = 0L

    private val handler = Handler(Looper.getMainLooper())

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            duration += 40L
            handler.postDelayed(this, 40L)
            listener.onTick(duration)
        }
    }

    fun start() {
        duration = 0L
        handler.postDelayed(runnable, 40L)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
    }
}