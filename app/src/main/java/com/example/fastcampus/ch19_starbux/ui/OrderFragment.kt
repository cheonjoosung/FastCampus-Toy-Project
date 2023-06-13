package com.example.fastcampus.ch19_starbux.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastcampus.R
import com.example.fastcampus.ch19_starbux.Menu
import com.example.fastcampus.ch19_starbux.MenuAdapter
import com.example.fastcampus.ch19_starbux.readData
import com.example.fastcampus.databinding.FragmentOrderStarbuxBinding
import kotlin.math.abs

class OrderFragment : Fragment(R.layout.fragment_order_starbux) {

    private lateinit var binding: FragmentOrderStarbuxBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderStarbuxBinding.bind(view)


        val menuData = context?.readData("menu.json", Menu::class.java) ?: return
        val menuAdapter = MenuAdapter().apply {
            submitList(menuData.coffee)
        }


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menuAdapter
        }

        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val seekPosition = abs(verticalOffset) / appBarLayout.totalScrollRange.toFloat()
            binding.motionLayout.progress = seekPosition
        }
    }


}