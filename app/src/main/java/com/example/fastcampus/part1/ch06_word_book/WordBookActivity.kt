package com.example.fastcampus.part1.ch06_word_book

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.fastcampus.databinding.ActivityWordBookBinding

class WordBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordBookBinding
    private lateinit var myWordAdapter: WordAdapter
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
        binding = ActivityWordBookBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding) {

            recyclerView.apply {
                addItemDecoration(DividerItemDecoration(applicationContext, LinearLayout.VERTICAL))
                myWordAdapter = WordAdapter(mutableListOf()).apply {
                    click = {
                        selectedWord = it
                        textTextView.text = it.text
                        meanTextView.text = it.mean

                        Toast.makeText(applicationContext, "Clicked $it", Toast.LENGTH_SHORT).show()
                    }
                }
                adapter = myWordAdapter
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
                            myWordAdapter.list.remove(it)
                            myWordAdapter.notifyDataSetChanged()
                            binding.textTextView.text = ""
                            binding.meanTextView.text = ""
                            Toast.makeText(applicationContext, "삭제 완료", Toast.LENGTH_SHORT).show()
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
            myWordAdapter.list.addAll(list)
            runOnUiThread {
                myWordAdapter.notifyItemRangeChanged(0, list.size)
            }
        }.start()
    }

    private fun updateAddWord() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let { word ->
                myWordAdapter.list.add(0, word)
                runOnUiThread { myWordAdapter.notifyDataSetChanged() }
            }
        }.start()
    }

    private fun editWord(word: Word) {
        val index = myWordAdapter.list.indexOfFirst { it.id == word.id }
        myWordAdapter.list[index] = word
        runOnUiThread {
            selectedWord = word
            myWordAdapter.notifyItemChanged(index)
            binding.textTextView.text = word.text
            binding.meanTextView.text = word.mean
        }
    }
}