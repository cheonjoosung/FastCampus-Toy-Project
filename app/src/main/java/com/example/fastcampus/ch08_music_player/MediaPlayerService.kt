package com.example.fastcampus.ch08_music_player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.example.fastcampus.CHANNEL
import com.example.fastcampus.CHANNEL_ID
import com.example.fastcampus.MEDIA_PLAYER_PAUSE
import com.example.fastcampus.MEDIA_PLAYER_PLAY
import com.example.fastcampus.MEDIA_PLAYER_STOP
import com.example.fastcampus.MainActivity
import com.example.fastcampus.R

class MediaPlayerService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? = null

    private val receiver = LowBatteryReceiver()

    override fun onCreate() {
        super.onCreate()

        Log.e("SERVICE", "onCreate")

        // 채널을 만들고 노티피케이션 추가가 가능
        createNotificationChannel()
        initReceiver()

        val playIcon = Icon.createWithResource(baseContext, R.drawable.baseline_play_arrow_24)
        val pauseIcon = Icon.createWithResource(baseContext, R.drawable.baseline_pause_24)
        val stopIcon = Icon.createWithResource(baseContext, R.drawable.baseline_stop_24)

        val mainPendingIntent = PendingIntent.getActivity(
            baseContext,
            0,
            Intent(baseContext, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        val pausePendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PAUSE },
            PendingIntent.FLAG_IMMUTABLE
        )

        val playPendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PLAY },
            PendingIntent.FLAG_IMMUTABLE
        )

        val stopPendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_STOP },
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = Notification.Builder(baseContext, CHANNEL_ID)
            .setStyle(
                Notification.MediaStyle().setShowActionsInCompactView(0, 1, 2)
            )
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.baseline_star_24)
            .addAction(
                Notification.Action.Builder(
                    pauseIcon, "Pause", pausePendingIntent
                ).build()
            )
            .addAction(
                Notification.Action.Builder(
                    playIcon, "Play", playPendingIntent
                ).build()
            )
            .addAction(
                Notification.Action.Builder(
                    stopIcon, "Stop", stopPendingIntent
                ).build()
            )
            .setContentIntent(mainPendingIntent)
            .setContentTitle("음악재생")
            .setContentText("음원이 재생 중 입니다.")
            .build()

        startForeground(100, notification)
    }

    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL, NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager = baseContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("SERVICE", "onStartCommand")

        when (intent?.action) {
            MEDIA_PLAYER_PLAY -> {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(baseContext, R.raw.electronic)
                }
                mediaPlayer?.start()
            }

            MEDIA_PLAYER_PAUSE -> {
                mediaPlayer?.pause()
            }

            MEDIA_PLAYER_STOP -> {
                mediaPlayer?.pause()
                mediaPlayer?.release()
                mediaPlayer = null
                stopSelf()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun initReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
        }

        registerReceiver(receiver, intentFilter)
    }
}