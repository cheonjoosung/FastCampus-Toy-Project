package com.example.fastcampus.ch18_tomorrow_house

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fastcampus.R
import com.example.fastcampus.databinding.FragmentAuthBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)

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
        val email = binding.emailEditText.toString()
        val password = binding.passwordEditText.toString()

        if (email.isEmpty() || password.isEmpty()) return

        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    initViewToSignInState()
                    Snackbar.make(binding.root, "회원가입에 성공했습니다.", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, "회원가입에 실패했습니다.", Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    private fun signIn() {
        val email = binding.emailEditText.toString()
        val password = binding.passwordEditText.toString()

        if (email.isEmpty() || password.isEmpty()) return

        if (Firebase.auth.currentUser == null) {
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        initViewToSignInState()
                        Snackbar.make(binding.root, "로그인에 성공했습니다.", Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(binding.root, "로그인에 실패했습니다.", Snackbar.LENGTH_SHORT).show()
                    }
                }
        } else {
            Firebase.auth.signOut()
            initViewToSignOutState()
        }
    }

    private fun initViewToSignInState() {
        with(binding) {
            emailEditText.setText(com.google.firebase.ktx.Firebase.auth.currentUser?.email)
            emailEditText.isEnabled = false
            passwordEditText.isVisible = false
            signUpButton.isEnabled = false
            signInButton.text = getString(com.example.fastcampus.R.string.sign_out)
        }
    }

    private fun initViewToSignOutState() {
        with(binding) {
            emailEditText.text.clear()
            emailEditText.isEnabled = true
            passwordEditText.isVisible = true
            passwordEditText.text.clear()
            signUpButton.isEnabled = true
            signInButton.text = getString(com.example.fastcampus.R.string.login)
        }
    }

    override fun onStart() {
        super.onStart()

        if (Firebase.auth.currentUser == null) {
            initViewToSignOutState()
        } else {
            initViewToSignInState()
        }
    }
}