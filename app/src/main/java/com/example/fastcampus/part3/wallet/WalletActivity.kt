package com.example.fastcampus.part3.wallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityWalletBinding

class WalletActivity : AppCompatActivity() {

    private val binding: ActivityWalletBinding by lazy {
        ActivityWalletBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}