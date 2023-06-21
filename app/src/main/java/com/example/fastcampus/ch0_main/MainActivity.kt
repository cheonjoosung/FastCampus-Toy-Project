package com.example.fastcampus.ch0_main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.fastcampus.ApiKey
import com.example.fastcampus.CommonUtils.getProjectList
import com.example.fastcampus.ch0_main.ProjectDetailActivity.Companion.PART_TYPE
import com.example.fastcampus.databinding.ActivityMainBinding
import com.kakao.sdk.common.KakaoSdk


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val projectList by lazy { getProjectList() }

    private val part1 by lazy { projectList.filter { it.type == Type.PART1 } }
    private val part2 by lazy { projectList.filter { it.type == Type.PART2 } }
    private val part3 by lazy { projectList.filter { it.type == Type.PART3 } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, ApiKey.KAKAO_KEY)

        if (part1.isNotEmpty()) {
            binding.part1RecyclerView.adapter = ProjectListAdapter(part1).apply {
                projectListClickListener = {
                    startProject(it.claasName)
                }
            }
        }

        if (part2.isNotEmpty()) {
            binding.part2RecyclerView.adapter = ProjectListAdapter(part2).apply {
                projectListClickListener = {
                    startProject(it.claasName)
                }
            }
        }

        if (part3.isNotEmpty()) {
            binding.part3RecyclerView.adapter = ProjectListAdapter(part3).apply {
                projectListClickListener = {
                    startProject(it.claasName)
                }
            }
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