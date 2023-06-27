package com.example.fastcampus

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.fastcampus.ch0_main.Project
import com.example.fastcampus.ch0_main.Type
import com.example.fastcampus.part1.ch01_count_number.CountNumberActivity
import com.example.fastcampus.part1.ch02_unit_conversion.UnitConversionActivity
import com.example.fastcampus.part1.ch03_emergency_medical.EmergencyMedicalCareActivity
import com.example.fastcampus.part1.ch04_calculator.CalculatorActivity
import com.example.fastcampus.part1.ch05_stop_watch.StopWatchActivity
import com.example.fastcampus.part1.ch06_word_book.WordBookActivity
import com.example.fastcampus.part1.ch07_my_gallery.MyGalleryActivity
import com.example.fastcampus.part1.ch08_music_player.MusicPlayerActivity
import com.example.fastcampus.part2.ch09_web_toon.WebToonActivity
import com.example.fastcampus.part2.ch10_recorder.RecorderActivity
import com.example.fastcampus.part2.ch11_today_notice.TodayNoticeActivity
import com.example.fastcampus.part2.ch12_github_repository.GithubRepositoryActivity
import com.example.fastcampus.part2.ch13_news.NewsActivity
import com.example.fastcampus.part2.ch14_chat.ChatActivity
import com.example.fastcampus.part2.ch15_weather.WeatherActivity
import com.example.fastcampus.part2.ch16_food_map.FoodMapActivity
import com.example.fastcampus.part2.ch17_genri.LoginGenriActivity
import com.example.fastcampus.part2.ch18_tomorrow_house.TomorrowHouseActivity
import com.example.fastcampus.part2.ch19_starbux.StarbuxActivity
import com.example.fastcampus.part2.ch20_youtube.YoutubeActivity
import com.example.fastcampus.part3.face_recognition.FaceRecognitionActivity
import com.example.fastcampus.part3.finance.FinanceActivity


fun Context.getProjectList(): List<Project> {
    return listOf(
        Project(
            getString(R.string.count_number),
            CountNumberActivity::class.java,
            Type.PART1,
            R.drawable.baseline_count
        ),
        Project(
            getString(R.string.unit_conversion),
            UnitConversionActivity::class.java,
            Type.PART1,
            R.drawable.baseline_convert
        ),
        Project(
            getString(R.string.emergency_medical),
            EmergencyMedicalCareActivity::class.java,
            Type.PART1,
            R.drawable.baseline_emergency_24
        ),
        Project(
            getString(R.string.calculator),
            CalculatorActivity::class.java,
            Type.PART1,
            R.drawable.baseline_calculate_24
        ),
        Project(
            getString(R.string.stop_watch),
            StopWatchActivity::class.java,
            Type.PART1,
            R.drawable.baseline_timer_24
        ),
        Project(
            getString(R.string.word_book),
            WordBookActivity::class.java,
            Type.PART1,
            R.drawable.baseline_bookmark_border_24
        ),
        Project(
            getString(R.string.my_gallery),
            MyGalleryActivity::class.java,
            Type.PART1,
            R.drawable.baseline_image_24
        ),
        Project(
            getString(R.string.music_player),
            MusicPlayerActivity::class.java,
            Type.PART1,
            R.drawable.baseline_play_circle_outline_24
        ),
        Project(
            getString(R.string.web_toon),
            WebToonActivity::class.java,
            Type.PART2,
            R.drawable.baseline_webtoon_24
        ),
        Project(
            getString(R.string.recorder),
            RecorderActivity::class.java,
            Type.PART2,
            R.drawable.baseline_fiber_smart_record_24
        ),
        Project(
            getString(R.string.today_notice),
            TodayNoticeActivity::class.java,
            Type.PART2,
            R.drawable.baseline_notifications_none_24
        ),
        Project(
            getString(R.string.github_repository),
            GithubRepositoryActivity::class.java,
            Type.PART2,
            R.drawable.baseline_star_border_24
        ),
        Project(
            getString(R.string.news),
            NewsActivity::class.java,
            Type.PART2,
            R.drawable.baseline_newspaper_24
        ),
        Project(
            getString(R.string.chat),
            ChatActivity::class.java,
            Type.PART2,
            R.drawable.baseline_chat_24
        ),
        Project(
            getString(R.string.weather),
            WeatherActivity::class.java,
            Type.PART2,
            R.drawable.baseline_wb_sunny_24
        ),
        Project(
            getString(R.string.map),
            FoodMapActivity::class.java,
            Type.PART2,
            R.drawable.baseline_map_24
        ),
        Project(
            getString(R.string.genri),
            LoginGenriActivity::class.java,
            Type.PART2,
            R.drawable.baseline_location_on_24
        ),
        Project(
            getString(R.string.tomorrow_house),
            TomorrowHouseActivity::class.java,
            Type.PART2,
            R.drawable.baseline_add_home_24
        ),
        Project(
            getString(R.string.star_bux),
            StarbuxActivity::class.java,
            Type.PART2,
            R.drawable.baseline_coffee
        ),
        Project(
            getString(R.string.youtube),
            YoutubeActivity::class.java,
            Type.PART2,
            R.drawable.baseline_youtube
        ),
        Project(
            getString(R.string.face_recognition),
            FaceRecognitionActivity::class.java,
            Type.PART3,
            R.drawable.baseline_face_24
        ),
        Project(
            getString(R.string.finance),
            FinanceActivity::class.java,
            Type.PART3,
            R.drawable.baseline_attach_money_24
        ),
    )
}

fun Context.toastMessage(msg: String? = "?") {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.loge(msg: String) {
    Log.e(this.javaClass.simpleName, msg)
}

fun EditText.setOnEditorActionListener(action: Int, invoke: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == action) {
            invoke()
            true
        } else
            false
    }
}

fun EditText.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.showKeyboardDelay() {
    postDelayed({ showKeyboard() }, 200)
}

fun EditText.hideKeyboard() {
    this.clearFocus()
    val inputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}
