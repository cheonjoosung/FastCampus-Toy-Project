package com.example.fastcampus.ch0_main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.fastcampus.ApiKey
import com.example.fastcampus.ch0_main.ProjectDetailActivity.Companion.PART_TYPE
import com.example.fastcampus.databinding.ActivityMainBinding
import com.example.fastcampus.getProjectList
import com.kakao.sdk.common.KakaoSdk


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val projectList by lazy { getProjectList() }

    private val part1 by lazy { projectList.filter { it.type == Type.PART1 } }
    private val part2 by lazy { projectList.filter { it.type == Type.PART2 } }
    private val part3 by lazy { projectList.filter { it.type == Type.PART3 } }

    private lateinit var part1Adapter: ProjectListAdapter
    private lateinit var part2Adapter: ProjectListAdapter
    private lateinit var part3Adapter: ProjectListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, ApiKey.KAKAO_KEY)

        if (part1.isNotEmpty()) {
            part1Adapter = ProjectListAdapter {
                startProject(it.claasName)
            }
            part1Adapter.submitList(part1)
            binding.part1RecyclerView.adapter = part1Adapter
        }

        if (part2.isNotEmpty()) {
            part2Adapter = ProjectListAdapter {
                startProject(it.claasName)
            }
            part2Adapter.submitList(part2)
            binding.part2RecyclerView.adapter = part2Adapter
        }

        if (part3.isNotEmpty()) {
            part3Adapter = ProjectListAdapter {
                startProject(it.claasName)
            }
            part3Adapter.submitList(part3)
            binding.part3RecyclerView.adapter = part3Adapter
        }

        with(binding) {
            part1TextView.apply {
                isVisible = part1.isNotEmpty()
                setOnClickListener { moveToProjectListActivity(Type.PART1) }
            }

            part2TextView.apply {
                isVisible = part2.isNotEmpty()
                setOnClickListener { moveToProjectListActivity(Type.PART2) }
            }

            part3TextView.apply {
                isVisible = part3.isNotEmpty()
                setOnClickListener { moveToProjectListActivity(Type.PART3) }
            }
        }

    }

    private fun startProject(claasName: Class<*>) {
        startActivity(
            Intent(this@MainActivity, claasName)
        )
    }

    private fun moveToProjectListActivity(type: Type) {
        startActivity(
            Intent(this@MainActivity, ProjectDetailActivity::class.java).apply {
                putExtra(PART_TYPE, type.ordinal + 1)
            }
        )
    }
}