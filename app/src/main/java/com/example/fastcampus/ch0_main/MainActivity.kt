package com.example.fastcampus.ch0_main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.R
import com.example.fastcampus.ch01_count_number.CountNumberActivity
import com.example.fastcampus.ch02_unit_conversion.UnitConversionActivity
import com.example.fastcampus.ch03_emergency_medical.EmergencyMedicalCareActivity
import com.example.fastcampus.ch04_calculator.CalculatorActivity
import com.example.fastcampus.ch05_stop_watch.StopWatchActivity
import com.example.fastcampus.ch06_word_book.WordBookActivity
import com.example.fastcampus.ch07_my_gallery.MyGalleryActivity
import com.example.fastcampus.ch08_music_player.MusicPlayerActivity
import com.example.fastcampus.ch09_web_toon.WebToonActivity
import com.example.fastcampus.ch10_recorder.RecorderActivity
import com.example.fastcampus.ch11_today_notice.TodayNoticeActivity
import com.example.fastcampus.ch12_github_repository.GithubRepositoryActivity
import com.example.fastcampus.ch13_news.NewsActivity
import com.example.fastcampus.ch14_chat.ChatActivity
import com.example.fastcampus.ch15_weather.WeatherActivity
import com.example.fastcampus.ch16_map.MapActivity
import com.example.fastcampus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val projectList = getProjectList()

        binding.rvAppList.adapter = ProjectListAdapter(projectList).apply {
            projectListClickListener = {
                Intent(this@MainActivity, it.claasName).run {
                    startActivity(this)
                }
            }
        }

    }

    private fun getProjectList(): List<ProjectList> {
        return listOf(
            ProjectList(getString(R.string.count_number), CountNumberActivity::class.java),
            ProjectList(getString(R.string.unit_conversion), UnitConversionActivity::class.java),
            ProjectList(
                getString(R.string.emergency_medical),
                EmergencyMedicalCareActivity::class.java
            ),
            ProjectList(getString(R.string.calculator), CalculatorActivity::class.java),
            ProjectList(getString(R.string.stop_watch), StopWatchActivity::class.java),
            ProjectList(getString(R.string.word_book), WordBookActivity::class.java),
            ProjectList(getString(R.string.my_gallery), MyGalleryActivity::class.java),
            ProjectList(getString(R.string.music_player), MusicPlayerActivity::class.java),
            ProjectList(getString(R.string.web_toon), WebToonActivity::class.java),
            ProjectList(getString(R.string.recorder), RecorderActivity::class.java),
            ProjectList(getString(R.string.today_notice), TodayNoticeActivity::class.java),
            ProjectList(getString(R.string.github_repository), GithubRepositoryActivity::class.java),
            ProjectList(getString(R.string.news), NewsActivity::class.java),
            ProjectList(getString(R.string.chat), ChatActivity::class.java),
            ProjectList(getString(R.string.weather), WeatherActivity::class.java),
            ProjectList(getString(R.string.map), MapActivity::class.java),
        )
    }
}