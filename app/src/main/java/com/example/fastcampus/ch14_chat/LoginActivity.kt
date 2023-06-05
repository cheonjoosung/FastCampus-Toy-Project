package com.example.fastcampus.ch14_chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fastcampus.DB_USERS
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            signUpButton.setOnClickListener {
                signUp()
            }

            signInButton.setOnClickListener {
                signIn()
            }
        }
    }

    private fun signUp() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.msg_email_password_input_error),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        getString(R.string.msg_success_sign_up),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, getString(R.string.msg_failed_sign_up), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun signIn() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.msg_email_password_input_error),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                val currentUser = Firebase.auth.currentUser
                if (task.isSuccessful && currentUser!= null) {

                    val userId =currentUser.uid
                    val user = mutableMapOf<String, Any>()
                    user["userId"] = userId
                    user["username"] = email

                    Firebase.database.reference.child(DB_USERS).child(userId).updateChildren(user)

                    Toast.makeText(
                        this,
                        getString(R.string.msg_success_sign_in),
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(
                        Intent(this, ChatActivity::class.java)
                    )
                } else {
                    Toast.makeText(this, getString(R.string.msg_failed_sign_in), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

}