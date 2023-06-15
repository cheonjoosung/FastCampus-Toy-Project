package com.example.fastcampus.part2.ch18_tomorrow_house.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fastcampus.R
import com.example.fastcampus.databinding.FragmentBookmarkBinding

class BookmarkFragment: Fragment(R.layout.fragment_bookmark) {

    private lateinit var binding: FragmentBookmarkBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookmarkBinding.bind(view)
    }
}