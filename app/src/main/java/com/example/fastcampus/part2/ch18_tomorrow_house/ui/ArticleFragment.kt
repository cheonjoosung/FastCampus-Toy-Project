package com.example.fastcampus.part2.ch18_tomorrow_house.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.fastcampus.R
import com.example.fastcampus.ch18_tomorrow_house.ui.ArticleFragmentArgs
import com.example.fastcampus.databinding.FragmentArticleBinding
import com.example.fastcampus.part2.ch18_tomorrow_house.ArticleModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var binding: FragmentArticleBinding

    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        val articleId = args.articleId
        if (articleId.isEmpty()) return

        binding.toolBar.setupWithNavController(findNavController())

        Firebase.firestore.collection("articles").document(articleId)
            .get()
            .addOnSuccessListener {
                val model = it.toObject<ArticleModel>()
                binding.descriptionTextView.text = model?.description ?: ""

                Glide.with(binding.thumbnailImageView)
                    .load(model?.imageUrl ?: "")
                    .into(binding.thumbnailImageView)
            }
            .addOnFailureListener {

            }
    }
}