package com.example.fastcampus.ch19_starbux.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fastcampus.R
import com.example.fastcampus.databinding.FragmentOrderStarbuxBinding

class OrderFragment: Fragment(R.layout.fragment_order_starbux) {

    private lateinit var binding: FragmentOrderStarbuxBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderStarbuxBinding.bind(view)
    }


}