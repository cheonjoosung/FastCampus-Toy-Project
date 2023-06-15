package com.example.fastcampus.part2.ch10_recorder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fastcampus.AUDIO_REQUEST_CODE
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityRecorderBinding
import java.io.IOException

class RecorderActivity : AppCompatActivity(), OnTimerTickListener {

    private lateinit var binding: ActivityRecorderBinding

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var fileName: String = ""

    enum class State {
        RELEASE, RECORDING, PLAYING
    }

    private var state = State.RELEASE

    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecorderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            recordButton.setOnClickListener {

                when (state) {
                    State.RELEASE -> {
                        checkRecordPermission()
                    }

                    State.RECORDING -> {
                        onRecord(false)
                    }

                    State.PLAYING -> {
                    }
                }

            }

            playButton.setOnClickListener {
                when (state) {
                    State.RELEASE -> {
                        onPlay(true)
                    }

                    State.RECORDING -> { /* do nothing */
                    }

                    State.PLAYING -> { /* do nothing */
                    }
                }
            }

            stopButton.setOnClickListener {
                when (state) {
                    State.RELEASE -> { /* do nothing */
                    }

                    State.RECORDING -> { /* do nothing */
                    }

                    State.PLAYING -> onPlay(false)
                }
            }

        }

        fileName = "${externalCacheDir?.absolutePath}/audioTest.3gp"
        timer = Timer(this)
    }

    private fun checkRecordPermission() {
        when {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> { // 녹음 시작
                onRecord(true)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this@RecorderActivity,
                Manifest.permission.RECORD_AUDIO
            ) -> {
                showPermissionRationalDialog()
            }

            else -> { // You can directly ask for the permission.
                ActivityCompat.requestPermissions(
                    this@RecorderActivity,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    AUDIO_REQUEST_CODE
                )
            }
        }
    }

    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun startRecording() {
        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this).apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(fileName)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                try {
                    prepare()
                } catch (e: IOException) {
                    Log.e(localClassName, "prepare() failed $e")
                }

                state = State.RECORDING
                start()
            }
        } else {
            MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(fileName)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                try {
                    prepare()
                } catch (e: IOException) {
                    Log.e(localClassName, "prepare() failed $e")
                }

                state = State.RECORDING
                start()
            }
        }

        binding.waveFormView.clearData()
        timer.start()

        binding.recordButton.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.baseline_stop_24
            )
        )
        binding.recordButton.imageTintList = ColorStateList.valueOf(Color.GRAY)

        binding.playButton.isEnabled = false
        binding.playButton.alpha = 0.3f
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        state = State.RELEASE

        binding.recordButton.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.baseline_fiber_manual_record_24
            )
        )

        timer.stop()

        binding.recordButton.imageTintList = ColorStateList.valueOf(Color.RED)

        binding.playButton.isEnabled = true
        binding.playButton.alpha = 1f
    }

    private fun onPlay(start: Boolean) = if (start) play() else stop()

    private fun play() {
        state = State.PLAYING

        player = MediaPlayer().apply {
            setDataSource(fileName)
            try {
                prepare()
            } catch (e: IOException) {
                Log.e(localClassName, "media player failed $e")
            }

            start()
        }

        player?.setOnCompletionListener {
            stop()
        }

        binding.recordButton.isEnabled = false
        binding.recordButton.alpha = 0.3f
        binding.waveFormView.clearWave()
        timer.start()
    }

    private fun stop() {
        state = State.RELEASE

        player?.release()
        player = null

        binding.recordButton.isEnabled = true
        binding.recordButton.alpha = 1f

        timer.stop()
    }

    private fun showPermissionRationalDialog() {
        AlertDialog.Builder(this)
            .setTitle("녹음 권한 요청")
            .setMessage("녹음 권한을 켜야 앱 사용이 가능합니다.")
            .setPositiveButton("권한 허용") { _, _ ->
                ActivityCompat.requestPermissions(
                    this@RecorderActivity,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    AUDIO_REQUEST_CODE
                )
            }
            .setNegativeButton("취소") { dialogInterface, _ -> dialogInterface.cancel() }
            .show()
    }

    private fun showPermissionSettingDialog() {
        AlertDialog.Builder(this)
            .setTitle("녹음 권한 요청")
            .setMessage("녹음 권한이 있어야 정상적 사용이 가능합니다. 앱 설정 화면으로 진입하셔서 권한을 켜주세요.")
            .setPositiveButton("권한 변경하러 가기") { _, _ -> navigateToAppSetting() }
            .setNegativeButton("취소") { dialogInterface, _ -> dialogInterface.cancel() }
            .show()
    }

    private fun navigateToAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val audioRecordPermissionGranted =
            (requestCode == AUDIO_REQUEST_CODE) && (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED)

        if (audioRecordPermissionGranted) {
            onRecord(true)
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@RecorderActivity,
                    Manifest.permission.RECORD_AUDIO
                )
            ) {
                showPermissionRationalDialog()
            } else {
                showPermissionSettingDialog()
            }
        }
    }

    override fun onTick(duration: Long) {

        val milliSecond = duration % 1000
        val second = (duration / 1000) % 60
        val minute = (duration / 1000 / 60)

        binding.timerTextView.text = String.format("%02d:%02d:%02d", minute, second, milliSecond / 10)

        if (state == State.PLAYING) {
            binding.waveFormView.replayAmplitude(duration.toInt())
        } else if (state == State.RECORDING){
            binding.waveFormView.addAmplitude(recorder?.maxAmplitude?.toFloat() ?: 0f)
        }
    }
}

interface OnTimerTickListener {
    fun onTick(duration: Long)
}