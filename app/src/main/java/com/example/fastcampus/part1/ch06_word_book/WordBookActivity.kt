package com.example.fastcampus.part1.ch06_word_book

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityWordBookBinding
import com.example.fastcampus.toastMessage

class WordBookActivity : AppCompatActivity() {

    private val binding: ActivityWordBookBinding by lazy {
        ActivityWordBookBinding.inflate(layoutInflater)
    }

    private lateinit var wordAdapter: WordAdapter

    private var selectedWord: Word? = null

    private val updateAddWordResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val isUpdated = result.data?.getBooleanExtra("isUpdated", false) ?: false
                if (isUpdated) {
                    updateAddWord()
                }
            }
        }

    private val updateEditResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedWord = result.data?.getParcelableExtra<Word>("updatedWord")
                updatedWord?.let { editWord(it) }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        wordAdapter = WordAdapter {
            selectedWord = it
            binding.textTextView.text = it.text
            binding.meanTextView.text = it.mean

            toastMessage(getString(R.string.msg_clicked_word, it.text))
        }

        with(binding) {

            recyclerView.apply {
                addItemDecoration(DividerItemDecoration(applicationContext, LinearLayout.VERTICAL))
                adapter = wordAdapter
            }

            addButton.setOnClickListener {
                updateAddWordResult.launch(Intent(applicationContext, AddActivity::class.java))
            }

            deleteImageView.setOnClickListener {
                if (selectedWord == null) return@setOnClickListener

                Thread {
                    selectedWord?.let {
                        AppDatabase.getInstance(applicationContext)?.wordDao()?.delete(it)

                        runOnUiThread {
                            val currentList = wordAdapter.currentList.toMutableList()
                            currentList.remove(it)
                            wordAdapter.submitList(currentList)
                            textTextView.text = ""
                            meanTextView.text = ""
                            toastMessage(getString(R.string.msg_delete_word_done))
                        }

                        selectedWord = null
                    }

                }.start()
            }

            editImageView.setOnClickListener {
                if (selectedWord == null) return@setOnClickListener

                val intent = Intent(this@WordBookActivity, AddActivity::class.java).apply {
                    putExtra("originWord", selectedWord)
                }
                updateEditResult.launch(intent)
            }
        }

        Thread {
            val list =
                AppDatabase.getInstance(applicationContext)?.wordDao()?.getAll() ?: emptyList()
            wordAdapter.submitList(list)
        }.start()
    }

    private fun updateAddWord() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let { word ->
                val currentList = wordAdapter.currentList.toMutableList()
                currentList.add(word)
                wordAdapter.submitList(currentList)
            }
        }.start()
    }

    private fun editWord(word: Word) {
        val currentList = wordAdapter.currentList.toMutableList()
        val index = currentList.indexOfFirst { it.id == word.id }
        currentList[index] = word

        runOnUiThread {
            wordAdapter.submitList(currentList)
            selectedWord = word
            binding.textTextView.text = word.text
            binding.meanTextView.text = word.mean
        }
    }
}