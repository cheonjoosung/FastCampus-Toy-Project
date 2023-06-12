package com.example.fastcampus.ch18_tomorrow_house.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fastcampus.R
import com.example.fastcampus.ch18_tomorrow_house.ArticleModel
import com.example.fastcampus.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val db = Firebase.firestore

        db.collection("articles").document("y49dU7ZBOd6zridRqtcG")
            .get()
            .addOnSuccessListener { result ->
                val article = result.toObject<ArticleModel>()
                Log.e("HomeFragment", article.toString())
            }
            .addOnFailureListener {
                Log.e("HomeFragment", it.message.toString())
            }

        binding.writeButton.setOnClickListener {
            setUpWriteButton(it)
        }
    }

    private fun setUpWriteButton(view: View) {
        if (Firebase.auth.currentUser == null) {
            Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_SHORT).show()
        } else {
            val action = HomeFragmentDirections.actionHomeFragmentToWriteArticleFragment()
            findNavController().navigate(action)
        }
    }
}