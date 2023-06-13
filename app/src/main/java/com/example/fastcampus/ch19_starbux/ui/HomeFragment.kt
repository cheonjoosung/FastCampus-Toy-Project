package com.example.fastcampus.ch19_starbux.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fastcampus.R
import com.example.fastcampus.databinding.FragmentHomeStarbuxBinding

class HomeFragment : Fragment(R.layout.fragment_home_starbux) {

    private lateinit var binding: FragmentHomeStarbuxBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeStarbuxBinding.bind(view)
    }

}