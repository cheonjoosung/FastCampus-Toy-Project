package com.example.fastcampus.ch0_main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.fastcampus.ApiKey
import com.example.fastcampus.CommonUtils.getProjectList
import com.example.fastcampus.databinding.ActivityMainBinding
import com.kakao.sdk.common.KakaoSdk


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, ApiKey.KAKAO_KEY)

        val projectList = getProjectList()
        val part1 = projectList.filter { it.type == Type.PART1 }
        val part2 = projectList.filter { it.type == Type.PART2 }
        val part3 = projectList.filter { it.type == Type.PART3 }

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
        Intent(this@MainActivity, claasName).run {
            startActivity(this)
        }
    }

    private fun moveToProjectListActivity(type: Type) {
        val partType = when (type) {
            Type.PART1 -> 1
            Type.PART2 -> 2
            else -> 3
        }

        startActivity(
            Intent(this@MainActivity, ProjectDetailActivity::class.java).apply {
                putExtra("partType", partType)
            }
        )
    }
}