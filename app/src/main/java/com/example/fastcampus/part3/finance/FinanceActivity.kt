package com.example.fastcampus.part3.finance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityFinanceBinding

class FinanceActivity : AppCompatActivity() {

    private val binding: ActivityFinanceBinding by lazy {
        ActivityFinanceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.view = this
    }

    fun openShuffle() {
        startActivity(
            Intent(this, PinActivity::class.java)
        )
    }

    fun openVerifyOtp() {
        startActivity(
            Intent(this, IdentityInputActivity::class.java)
        )
    }
}