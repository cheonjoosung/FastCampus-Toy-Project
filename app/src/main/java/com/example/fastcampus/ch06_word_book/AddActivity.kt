package com.example.fastcampus.ch06_word_book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityAddBinding
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    private val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            typeChipGroup.apply {
                types.forEach { text ->
                    addView(createChip(text))
                }
            }

            addButton.setOnClickListener {
                val text = textInputEditText.text.toString()
                val mean = meanTextInputEditText.text.toString()
                val type = findViewById<Chip>(typeChipGroup.checkedChipId).text.toString()

                val word = Word(text, mean, type)

                Thread {
                    AppDatabase.getInstance(applicationContext)?.wordDao()?.insert(word)
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "$word 추가",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }.start()
            }
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isCheckable = true
        }
    }
}