package com.example.fastcampus.ch05_stop_watch

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityStopWatchBinding
import com.example.fastcampus.databinding.DialogCountdownSettingBinding
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.math.max

class StopWatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStopWatchBinding

    private var countDownSecond = 10
    private var currentDeciSecond = 0 //0.1초 단위
    private var currentCountDownDeciSecond = countDownSecond * 10

    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCountView()

        with(binding) {

            startButton.setOnClickListener {
                start()
                setVisibility(start = false, stop = false, pause = true, lap = true)
            }

            stopButton.setOnClickListener {
                showAlertDialog()
            }

            pauseButton.setOnClickListener {
                pause()
                setVisibility(start = true, stop = true, pause = false, lap = false)
            }

            lapButton.setOnClickListener {
                lap()
            }

            countDownTextView.setOnClickListener {
                countDownSettingDialog()
            }

        }
    }

    private fun initCountView() {
        binding.countDownTextView.text = String.format("%02d", countDownSecond)
        currentCountDownDeciSecond = countDownSecond * 10
        binding.countDownProgressBar.progress = 100
    }

    private fun start() {

        timer = timer(initialDelay = 0, period = 100) {

            if (currentCountDownDeciSecond == 0) {
                currentDeciSecond += 1

                val minute = currentDeciSecond.div(10) / 60
                val second = currentDeciSecond.div(10) % 60
                val deciSecond = currentDeciSecond % 10

                runOnUiThread {
                    binding.timeTextView.text = String.format("%02d:%02d", minute, second)
                    binding.tickTextView.text = deciSecond.toString()

                    binding.groupCountDown.isVisible = false // 뷰 3개를 helper > group 으로 이용하면 간편함
                }
            } else {
                currentCountDownDeciSecond -= 1
                val second = currentCountDownDeciSecond / 10
                val progress = (currentCountDownDeciSecond / (countDownSecond * 10f)) * 100

                binding.root.post {
                    binding.countDownTextView.text = String.format("%02d", second)
                    binding.countDownProgressBar.progress = progress.toInt()
                }

            }
        }
    }

    private fun stop() {
        setVisibility(start = true, stop = true, pause = false, lap = false)

        binding.timeTextView.text = getString(R.string.zero_time)
        binding.tickTextView.text = getString(R.string.zero)

        binding.groupCountDown.isVisible = true // 뷰 3개를 helper > group 으로 이용하면 간편함
        currentDeciSecond = 0
        initCountView()
    }

    private fun pause() {
        timer?.cancel()
        timer = null
    }

    private fun lap() {

    }

    private fun setVisibility(start: Boolean, stop: Boolean, pause: Boolean, lap: Boolean) {
        with(binding) {
            startButton.isVisible = start
            stopButton.isVisible = stop
            pauseButton.isVisible = pause
            lapButton.isVisible = lap
        }

    }

    private fun countDownSettingDialog() {
        binding.stopButton.postDelayed({}, 1000L)
        AlertDialog.Builder(this).apply {
            val dialogBinding = DialogCountdownSettingBinding.inflate(layoutInflater)
            with(dialogBinding.countDownSecondPicker) {
                maxValue = 20
                minValue = 0
                value = countDownSecond
            }
            setView(dialogBinding.root)
            setMessage("카운트 다운?")
            setPositiveButton("확인") { _, _ ->
                countDownSecond = dialogBinding.countDownSecondPicker.value
                currentCountDownDeciSecond = countDownSecond * 10
                binding.countDownTextView.text = String.format("%02d", countDownSecond)
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            title = "Title"
            setMessage("종료 할래?")
            setPositiveButton("응") { _, _ ->
                stop()
            }
            setNegativeButton("아니", null)
        }.show()
    }
}