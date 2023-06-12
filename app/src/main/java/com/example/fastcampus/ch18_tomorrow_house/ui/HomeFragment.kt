package com.example.fastcampus.ch18_tomorrow_house.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fastcampus.R
import com.example.fastcampus.ch18_tomorrow_house.ArticleModel
import com.example.fastcampus.ch18_tomorrow_house.HomeArticleAdapter
import com.example.fastcampus.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var articleAdapter: HomeArticleAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)


        binding.writeButton.setOnClickListener {
            setUpWriteButton(it)
        }

        articleAdapter = HomeArticleAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment(
                articleId = it.articleId.orEmpty()
            ))
        }

        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = articleAdapter
        }

        loadDataFromFirebase()
    }

    private fun setUpWriteButton(view: View) {
        if (Firebase.auth.currentUser == null) {
            Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_SHORT).show()
        } else {
            val action = HomeFragmentDirections.actionHomeFragmentToWriteArticleFragment()
            findNavController().navigate(action)
        }
    }

    private fun loadDataFromFirebase() {
        Firebase.firestore.collection("articles")
            .get()
            .addOnSuccessListener { result ->
                val list = result.map {
                    it.toObject<ArticleModel>()
                }

                articleAdapter.submitList(list)
            }
    }
}