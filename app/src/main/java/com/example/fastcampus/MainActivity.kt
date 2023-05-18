package com.example.fastcampus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.ch03_calculator.CalculatorActivity
import com.example.fastcampus.ch01_count_number.CountNumberActivity
import com.example.fastcampus.databinding.ActivityMainBinding
import com.example.fastcampus.ch04_emergency_medical.EmergencyMedicalCareActivity
import com.example.fastcampus.ch02_unit_conversion.UnitConversionActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = mutableListOf<ProjectList>().apply {
            add(ProjectList("숫자 세기", CountNumberActivity::class.java))
            add(ProjectList("단위 변환기", UnitConversionActivity::class.java))
            add(ProjectList("응급 의료 정보", EmergencyMedicalCareActivity::class.java))
            add(ProjectList("계산기", CalculatorActivity::class.java))
        }

        binding.rvAppList.adapter = ProjectListAdapter(list).apply {
            projectListClickListener = {
                Intent(this@MainActivity, it.claasName).run {
                    startActivity(this)
                }
            }
        }
    }
}

data class ProjectList(
    val name: String,
    val claasName: Class<*>
)