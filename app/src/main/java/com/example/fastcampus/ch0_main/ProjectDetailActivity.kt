package com.example.fastcampus.ch0_main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fastcampus.CommonUtils.getProjectList
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityProjectDetailBinding

class ProjectDetailActivity : AppCompatActivity() {

    companion object {
        const val PART_TYPE = "partType"
    }

    private lateinit var binding: ActivityProjectDetailBinding
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