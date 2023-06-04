package com.example.fastcampus.ch14_chat.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastcampus.R
import com.example.fastcampus.ch14_chat.LoginActivity
import com.example.fastcampus.databinding.FragmentMypageBinding
import com.example.fastcampus.databinding.FragmentUserlistBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment(R.layout.fragment_mypage) {

    private lateinit var binding: FragmentMypageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMypageBinding.bind(view)

        with(binding) {
            applyButton.setOnClickListener {
                change()
            }

            signOutButton.setOnClickListener {
                signOut()
            }
        }
    }

    private fun change() {
        val username = binding.userEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()

        if (username.isEmpty()) {
            Toast.makeText(
                context,
                getString(R.string.msg_name_input_error),
                Toast.LENGTH_SHORT
            ).show()
            return
        }


    }

    private fun signOut() {
        Firebase.auth.signOut()
        startActivity(Intent(context, LoginActivity::class.java))
        requireActivity().finish()
    }
}