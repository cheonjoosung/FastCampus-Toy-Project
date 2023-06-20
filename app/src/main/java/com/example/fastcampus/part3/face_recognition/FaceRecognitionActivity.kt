package com.example.fastcampus.part3.face_recognition

import android.graphics.PointF
import android.graphics.RectF
import android.os.Bundle
import android.util.SizeF
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.example.face_recognition.Camera
import com.example.face_recognition.recognition.FaceAnalyzerListener
import com.example.fastcampus.databinding.ActivityFaceRecognitionBinding

class FaceRecognitionActivity : AppCompatActivity(), FaceAnalyzerListener {

    private val binding: ActivityFaceRecognitionBinding by lazy {
        ActivityFaceRecognitionBinding.inflate(layoutInflater)
    }

    private val camera = Camera(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setProgressText("시작하기를 눌러주세요")

        camera.initCamera(binding.cameraLayout, this)

        with(binding) {
            startDetectButton.setOnClickListener {
                it.isVisible = false
                camera.startFaceDetect()
                setProgressText("얼굴을 보여주세요")
            }
        }

    }

    override fun detect() {
    }

    override fun stopDetect() {
        camera.stopFaceDetect()
        reset()
    }

    override fun notDetect() {
    }

    override fun detectProgress(progress: Float, msg: String) {
        setProgressText(msg)
    }

    override fun faceSize(rectF: RectF, sizeF: SizeF, pointF: PointF) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun reset() {
        binding.startDetectButton.isVisible = true
    }

    private fun setProgressText(text: String) {
        TransitionManager.beginDelayedTransition(binding.root)
        binding.progressTextView.text = text
    }
}