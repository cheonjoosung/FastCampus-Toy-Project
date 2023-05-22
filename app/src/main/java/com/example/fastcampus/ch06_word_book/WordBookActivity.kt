package com.example.fastcampus.ch06_word_book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityWordBookBinding

class WordBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dummyList = mutableListOf<Word>().apply {
            add(Word("weather", "날씨", "명사"))
            add(Word("honey", "꿀", "명사"))
            add(Word("run", "실행하다", "동사"))
        }

        with(binding) {

            recyclerView.apply {
                addItemDecoration(DividerItemDecoration(applicationContext, LinearLayout.VERTICAL))
                adapter = WordAdapter(mutableListOf()).apply {
                    click = {
                        Toast.makeText(applicationContext, "Clicked $it", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            addButton.setOnClickListener {
                startActivity(Intent(applicationContext, AddActivity::class.java))
            }
        }
    }
}