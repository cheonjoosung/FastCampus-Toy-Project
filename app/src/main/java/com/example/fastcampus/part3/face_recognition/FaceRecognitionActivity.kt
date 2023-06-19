package com.example.fastcampus.part3.face_recognition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityFaceRecognitionBinding

class FaceRecognitionActivity : AppCompatActivity() {

    private val binding: ActivityFaceRecognitionBinding by lazy {
        ActivityFaceRecognitionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            startDetectButton.setOnClickListener {
                it.isVisible = false
            }
        }
    }
}