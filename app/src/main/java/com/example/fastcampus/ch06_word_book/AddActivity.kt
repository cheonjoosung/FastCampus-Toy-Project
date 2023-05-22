package com.example.fastcampus.ch06_word_book

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import com.example.fastcampus.databinding.ActivityAddBinding
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    private var originWord: Word? = null

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
                if (textInputEditText.text.isNullOrEmpty() || typeChipGroup.checkedChipId == -1) {
                    Toast.makeText(applicationContext, "단어 또는 칩을 선택해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (originWord == null) add() else modify()
            }

            textInputEditText.addTextChangedListener {
                it?.let { text ->
                    binding.textTextInputLayout.error = when (text.length) {
                        0 -> "값을 입력"
                        1 -> "2자 이상 입력"
                        else -> null
                    }
                }
            }
        }

        intent.getParcelableExtra<Word>("originWord")?.let {word ->
            originWord = word
            binding.textInputEditText.setText(word.text)
            binding.meanTextInputEditText.setText(word.mean)
            val chip = binding.typeChipGroup.children.firstOrNull { (it as Chip).text == word.type } as Chip
            chip.isChecked = true
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isCheckable = true
        }
    }

    private fun add() {
        with (binding) {
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
                intent.putExtra("isUpdated", true)
                setResult(RESULT_OK, intent)
                finish()
            }.start()
        }
    }

    private fun modify() {
        with(binding) {
            val text = textInputEditText.text.toString()
            val mean = meanTextInputEditText.text.toString()
            val type = findViewById<Chip>(typeChipGroup.checkedChipId).text.toString()

            val word = originWord?.copy(text = text, mean = mean, type = type)

            Thread {
                word?.let {
                    AppDatabase.getInstance(applicationContext)?.wordDao()?.update(word)
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "$word 수정",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    intent.putExtra("updatedWord", it)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }.start()
        }
    }
}