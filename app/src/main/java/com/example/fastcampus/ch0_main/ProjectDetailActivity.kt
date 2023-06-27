package com.example.fastcampus.ch0_main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityProjectDetailBinding
import com.example.fastcampus.getProjectList

class ProjectDetailActivity : AppCompatActivity() {

    companion object {
        const val PART_TYPE = "partType"
    }

    private lateinit var binding: ActivityProjectDetailBinding

    private lateinit var adapter: ProjectListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getIntExtra(PART_TYPE, -1)
        if (type == -1) finish()
        else {
            val list = getProjectList().filter {
                type == (it.type.ordinal + 1)
            }

            adapter = ProjectListAdapter {
                Intent(this@ProjectDetailActivity, it.claasName).run {
                    startActivity(this)
                }
            }

            adapter.submitList(list)

            binding.projectListRecyclerView.layoutManager =
                GridLayoutManager(this@ProjectDetailActivity, 2)

            binding.projectListRecyclerView.adapter = adapter

            binding.titleTextView.text = when (type) {
                1 -> getString(R.string.part_1_title)
                2 -> getString(R.string.part_2_title)
                else -> getString(R.string.part_3_title)
            }
        }
    }
}