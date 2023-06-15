package com.example.fastcampus.ch0_main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fastcampus.CommonUtils.getProjectList
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityProjectDetailBinding

class ProjectDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getIntExtra("partType", -1)
        if (type == -1) finish()
        else {
            val list = getProjectList().filter {
                when (type) {
                    1 -> it.type == Type.PART1
                    2 -> it.type == Type.PART2
                    else -> it.type == Type.PART3
                }
            }

            binding.projectListRecyclerView.layoutManager =
                GridLayoutManager(this@ProjectDetailActivity, 2)

            binding.projectListRecyclerView.adapter = ProjectListAdapter(list).apply {
                projectListClickListener = {
                    Intent(this@ProjectDetailActivity, it.claasName).run {
                        startActivity(this)
                    }
                }
            }

            binding.titleTextView.text = when (type) {
                1 -> getString(R.string.part_1_title)
                2 -> getString(R.string.part_2_title)
                else -> getString(R.string.part_3_title)
            }
        }
    }
}