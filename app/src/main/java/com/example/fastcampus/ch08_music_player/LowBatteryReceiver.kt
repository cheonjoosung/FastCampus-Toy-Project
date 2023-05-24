package com.example.fastcampus.ch08_music_player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class LowBatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.e(javaClass.simpleName, "__ ${intent.action}")

        when (intent.action) {
            Intent.ACTION_BATTERY_LOW -> {
                Toast.makeText(context, "배터리가 부족한데??", Toast.LENGTH_SHORT).show()
            }
        }
    }
}