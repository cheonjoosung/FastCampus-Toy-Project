package com.example.fastcampus.ch19_starbux.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.fastcampus.R
import com.example.fastcampus.ch19_starbux.readData
import com.example.fastcampus.databinding.FragmentHomeStarbuxBinding

class HomeFragment : Fragment(R.layout.fragment_home_starbux) {

    private lateinit var binding: FragmentHomeStarbuxBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeStarbuxBinding.bind(view)

        val homeData = context?.readData() ?: return

        binding.appBarTitleTextView.text =
            getString(R.string.msg_coffee_recommend, homeData.user.nickname)

        binding.starCountTextView.text =
            getString(R.string.star_of_star, homeData.user.starCount, homeData.user.totalCount)

        binding.progressBar.progress = homeData.user.starCount
        binding.progressBar.max = homeData.user.totalCount

        Glide.with(binding.appBarImageView)
            .load(homeData.appbarImage)
            .into(binding.appBarImageView)
    }

}