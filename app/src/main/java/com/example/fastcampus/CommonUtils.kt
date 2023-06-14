package com.example.fastcampus

import android.content.Context
import com.example.fastcampus.ch01_count_number.CountNumberActivity
import com.example.fastcampus.ch02_unit_conversion.UnitConversionActivity
import com.example.fastcampus.ch03_emergency_medical.EmergencyMedicalCareActivity
import com.example.fastcampus.ch04_calculator.CalculatorActivity
import com.example.fastcampus.ch05_stop_watch.StopWatchActivity
import com.example.fastcampus.ch06_word_book.WordBookActivity
import com.example.fastcampus.ch07_my_gallery.MyGalleryActivity
import com.example.fastcampus.ch08_music_player.MusicPlayerActivity
import com.example.fastcampus.ch09_web_toon.WebToonActivity
import com.example.fastcampus.ch0_main.Project
import com.example.fastcampus.ch0_main.Type
import com.example.fastcampus.ch10_recorder.RecorderActivity
import com.example.fastcampus.ch11_today_notice.TodayNoticeActivity
import com.example.fastcampus.ch12_github_repository.GithubRepositoryActivity
import com.example.fastcampus.ch13_news.NewsActivity
import com.example.fastcampus.ch14_chat.ChatActivity
import com.example.fastcampus.ch15_weather.WeatherActivity
import com.example.fastcampus.ch16_food_map.FoodMapActivity
import com.example.fastcampus.ch17_genri.LoginGenriActivity
import com.example.fastcampus.ch18_tomorrow_house.TomorrowHouseActivity
import com.example.fastcampus.ch19_starbux.StarbuxActivity
import com.example.fastcampus.ch20_youtube.YoutubeActivity

object CommonUtils {
    fun Context.getProjectList(): List<Project> {
        return listOf(
            Project(getString(R.string.count_number), CountNumberActivity::class.java, Type.PART1),
            Project(
                getString(R.string.unit_conversion),
                UnitConversionActivity::class.java,
                Type.PART1
            ),
            Project(
                getString(R.string.emergency_medical),
                EmergencyMedicalCareActivity::class.java, Type.PART1
            ),
            Project(getString(R.string.calculator), CalculatorActivity::class.java, Type.PART1),
            Project(getString(R.string.stop_watch), StopWatchActivity::class.java, Type.PART1),
            Project(getString(R.string.word_book), WordBookActivity::class.java, Type.PART1),
            Project(getString(R.string.my_gallery), MyGalleryActivity::class.java, Type.PART1),
            Project(getString(R.string.music_player), MusicPlayerActivity::class.java, Type.PART1),
            Project(getString(R.string.web_toon), WebToonActivity::class.java, Type.PART2),
            Project(getString(R.string.recorder), RecorderActivity::class.java, Type.PART2),
            Project(getString(R.string.today_notice), TodayNoticeActivity::class.java, Type.PART2),
            Project(
                getString(R.string.github_repository),
                GithubRepositoryActivity::class.java,
                Type.PART2
            ),
            Project(getString(R.string.news), NewsActivity::class.java, Type.PART2),
            Project(getString(R.string.chat), ChatActivity::class.java, Type.PART2),
            Project(getString(R.string.weather), WeatherActivity::class.java, Type.PART2),
            Project(getString(R.string.map), FoodMapActivity::class.java, Type.PART2),
            Project(getString(R.string.genri), LoginGenriActivity::class.java, Type.PART2),
            Project(
                getString(R.string.tomorrow_house),
                TomorrowHouseActivity::class.java,
                Type.PART2
            ),
            Project(getString(R.string.star_bux), StarbuxActivity::class.java, Type.PART2),
            Project(getString(R.string.youtube), YoutubeActivity::class.java, Type.PART2),
        )
    }
}