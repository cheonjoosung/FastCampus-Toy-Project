package com.example.fastcampus.ch19_starbux.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.fastcampus.R
import com.example.fastcampus.ch19_starbux.Home
import com.example.fastcampus.ch19_starbux.Menu
import com.example.fastcampus.ch19_starbux.MenuView
import com.example.fastcampus.ch19_starbux.readData
import com.example.fastcampus.databinding.FragmentHomeStarbuxBinding

class HomeFragment : Fragment(R.layout.fragment_home_starbux) {

    private lateinit var binding: FragmentHomeStarbuxBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeStarbuxBinding.bind(view)

        val homeData = context?.readData("home.json", Home::class.java) ?: return
        val menuData = context?.readData("menu.json", Menu::class.java) ?: return

        with(binding) {
            appBarTitleTextView.text =
                getString(R.string.msg_coffee_recommend, homeData.user.nickname)

            starCountTextView.text =
                getString(R.string.star_of_star, homeData.user.starCount, homeData.user.totalCount)

            progressBar.max = homeData.user.totalCount

            ValueAnimator.ofInt(0, homeData.user.starCount).apply {
                duration = 1000
                addUpdateListener {
                    binding.progressBar.progress = it.animatedValue as Int
                }
                start()
            }


            Glide.with(appBarImageView)
                .load(homeData.appbarImage)
                .into(appBarImageView)


            recommendCoffeeMenuList.titleTextView.text = getString(R.string.msg_recommend_menu, homeData.user.nickname, "음료")
            menuData.coffee.forEach {
                binding.recommendCoffeeMenuList.menuLayout.addView(
                    MenuView(context = requireContext()).apply {
                        setTitle(it.name)
                        setImageView(it.image)
                    }
                )
            }

            recommendFoodMenuList.titleTextView.text = getString(R.string.msg_recommend_menu, homeData.user.nickname, "푸드")
            menuData.food.forEach {
                recommendFoodMenuList.menuLayout.addView(
                    MenuView(context = requireContext()).apply {
                        setTitle(it.name)
                        setImageView(it.image)
                    }
                )
            }

            Glide.with(bannerLayout.bannerImageView)
                .load(homeData.banner.image)
                .into(bannerLayout.bannerImageView)

            scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY == 0) {
                    extendFloatingActionButton.extend()
                } else {
                    extendFloatingActionButton.shrink()
                }
            }

        }

    }

}