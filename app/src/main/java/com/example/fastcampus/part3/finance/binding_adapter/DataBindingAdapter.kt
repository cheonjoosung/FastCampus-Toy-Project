package com.example.fastcampus.part3.finance.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.fastcampus.R


@BindingAdapter(value = ["code_text", "code_index"])
fun ImageView.setPin(codeText: CharSequence?, index: Int) {
    if (codeText != null) {
        if (codeText.length > index) {
            setImageResource(R.drawable.baseline_circle_black_24)
        } else {
            setImageResource(R.drawable.baseline_circle_grey_24)
        }
    }
}