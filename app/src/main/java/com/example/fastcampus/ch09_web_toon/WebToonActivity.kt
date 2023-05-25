package com.example.fastcampus.ch09_web_toon

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityWebToonBinding

class WebToonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebToonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebToonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            btn1.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, WebViewFragment())
                    commit()
                }

            }

            btn2.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, BFragment())
                    commit()
                }
            }
        }
    }
}