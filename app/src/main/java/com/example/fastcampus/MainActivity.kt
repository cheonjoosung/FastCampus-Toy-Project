package com.example.fastcampus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastcampus.countnumber.CountNumberActivity
import com.example.fastcampus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = mutableListOf<ProjectList>().apply {
            add(ProjectList("숫자 세기", CountNumberActivity::class.java))
        }

        binding.rvAppList.adapter = ProjectListAdapter(list).apply {
            projectListClickListener = {
                val it = Intent(this@MainActivity, it.claasName)
                startActivity(it)
            }
        }
    }
}

data class ProjectList(
    val name: String,
    val claasName: Class<*>
)