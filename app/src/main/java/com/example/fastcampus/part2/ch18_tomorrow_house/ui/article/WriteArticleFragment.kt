package com.example.fastcampus.part2.ch18_tomorrow_house.ui.article

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fastcampus.R
import com.example.fastcampus.ch18_tomorrow_house.ui.article.WriteArticleFragmentDirections
import com.example.fastcampus.databinding.FragmentWriteBinding
import com.example.fastcampus.part2.ch18_tomorrow_house.ArticleModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class WriteArticleFragment : Fragment(R.layout.fragment_write) {

    private var selectedUri: Uri? = null

    private lateinit var binding: FragmentWriteBinding

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            selectedUri = uri
            binding.photoImageView.setImageURI(uri)
        } else {
            Log.e("WriteArticleFragment", "No media selected")
            Toast.makeText(context, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWriteBinding.bind(view)

        binding.photoImageView.setOnClickListener {
            startPicker()
        }

        binding.submitButton.setOnClickListener {
            showProgress()
            submit(view)
        }

        binding.addButton.setOnClickListener {
            startPicker()
            binding.clearButton.isVisible = true
            binding.addButton.isVisible = false
        }

        binding.clearButton.setOnClickListener {
            binding.photoImageView.setImageURI(null)
            selectedUri = null
            binding.clearButton.isVisible = false
            binding.addButton.isVisible = true
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(WriteArticleFragmentDirections.actionBack())
        }
    }

    private fun startPicker() {
        if (selectedUri == null)
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun submit(view: View) {
        if (selectedUri == null) {
            Snackbar.make(view, "이미지가 선택되지 않았습니다.", Snackbar.LENGTH_SHORT).show()
            hideProgress()
            return
        }

        val photoUri = selectedUri ?: return
        upload(photoUri, view,
            successHandler = {
                uploadArticle(it, binding.descriptionEditText.text.toString())
            },
            errorHandler = {
                Snackbar.make(view, "업로드가 실패했습니다.", Snackbar.LENGTH_SHORT).show()
                hideProgress()
            }
        )
    }

    private fun upload(
        photoUri: Uri,
        view: View,
        successHandler: (String) -> Unit,
        errorHandler: (Throwable?) -> Unit
    ) {
        val fileName = "${UUID.randomUUID()}.png"
        Firebase.storage.reference.child("articles/photo").child(fileName)
            .putFile(photoUri)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(view, "업로드가 성공했습니다.", Snackbar.LENGTH_SHORT).show()
                    Firebase.storage.reference.child("articles/photo/$fileName")
                        .downloadUrl
                        .addOnSuccessListener {
                            successHandler(it.toString())
                        }
                        .addOnFailureListener {
                            errorHandler(it)
                        }
                } else {
                    errorHandler(task.exception)
                }
            }
    }

    private fun uploadArticle(photoUrl: String, description: String) {
        val articleId = UUID.randomUUID().toString()
        val articleModel = ArticleModel(
            articleId = articleId,
            createdAt = System.currentTimeMillis(),
            description = description,
            imageUrl = photoUrl
        )

        Firebase.firestore.collection("articles").document(articleId).set(articleModel)
            .addOnSuccessListener {
                findNavController().navigate(WriteArticleFragmentDirections.actionWriteArticleFragmentToHomeFragment())
                hideProgress()
            }
            .addOnFailureListener {
                hideProgress()
                it.printStackTrace()
            }

    }

    private fun showProgress() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() {
        binding.progressBar.isVisible = false
    }
}