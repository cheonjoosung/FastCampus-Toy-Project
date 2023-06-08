package com.example.fastcampus.ch17_genri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fastcampus.databinding.ActivityGenriBinding
import com.kakao.sdk.common.util.Utility

class GenriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keyHash = Utility.getKeyHash(this)
        Log.e(localClassName, keyHash)

        startActivity(Intent(this, LoginActivity::class.java))
    }
}