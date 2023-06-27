package com.example.fastcampus.part3.finance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityIdentifyInputBinding
import com.example.fastcampus.hideKeyboard
import com.example.fastcampus.setOnEditorActionListener
import com.example.fastcampus.showKeyboard
import com.example.fastcampus.showKeyboardDelay

class IdentityInputActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityIdentifyInputBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.view = this

        initView()
        binding.nameEdit.showKeyboardDelay()
    }

    private fun initView() {
        with(binding) {
            nameEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_NEXT) {
                birthdayLayout.isVisible = true
                birthdayEdit.showKeyboard()
            }

            birthdayEdit.doAfterTextChanged {
                if (birthdayEdit.length() > 7) {
                    genderLayout.isVisible = true
                    birthdayEdit.hideKeyboard()
                }
            }

            genderChipGroup.setOnCheckedChangeListener { group, checkedId ->
                if (!telecomLayout.isVisible) {
                    telecomLayout.isVisible = true
                }
            }

            telecomChipGroup.setOnCheckedChangeListener { group, checkedId ->
                if (!phoneLayout.isVisible) {
                    phoneLayout.isVisible = true
                    phoneEdit.showKeyboard()
                }
            }

            phoneEdit.doAfterTextChanged {
                if (phoneEdit.length() > 10) {
                    confirmButton.isVisible = true
                    phoneEdit.hideKeyboard()
                }
            }

            phoneEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
                if (phoneEdit.length() > 9) {
                    confirmButton.isVisible = true
                    phoneEdit.hideKeyboard()
                }
            }
        }
    }
}