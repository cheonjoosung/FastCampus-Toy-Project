package com.example.fastcampus.ch18_tomorrow_house

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityTommorowHouseBinding

class TomorrowHouseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTommorowHouseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTommorowHouseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }
}