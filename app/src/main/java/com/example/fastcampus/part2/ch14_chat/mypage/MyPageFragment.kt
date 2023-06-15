package com.example.fastcampus.part2.ch14_chat.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fastcampus.DB_USERS
import com.example.fastcampus.R
import com.example.fastcampus.databinding.FragmentMypageBinding
import com.example.fastcampus.part2.ch14_chat.LoginActivity
import com.example.fastcampus.part2.ch14_chat.userlist.UserItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment(R.layout.fragment_mypage) {

    private lateinit var binding: FragmentMypageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMypageBinding.bind(view)

        val currentUserId = Firebase.auth.currentUser?.uid ?: ""
        val currentUserDB = Firebase.database.reference.child(DB_USERS).child(currentUserId)

        currentUserDB.get().addOnSuccessListener {
            val currentUserItem = it.getValue(UserItem::class.java) ?: return@addOnSuccessListener

            binding.userEditText.setText(currentUserItem.username)
            binding.descriptionEditText.setText(currentUserItem.description)
        }

        with(binding) {
            applyButton.setOnClickListener {
                change(currentUserDB)
            }

            signOutButton.setOnClickListener {
                signOut()
            }
        }
    }

    private fun change(currentUserDB: DatabaseReference) {
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

        val user = mutableMapOf<String, Any>()
        user["username"] = username
        user["description"] = description
        currentUserDB.updateChildren(user)
    }

    private fun signOut() {
        Firebase.auth.signOut()
        startActivity(Intent(context, LoginActivity::class.java))
        requireActivity().finish()
    }
}