package com.example.fastcampus.part3.finance

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fastcampus.databinding.ActivityPinBinding
import com.example.fastcampus.part3.finance.widget.ShuffleNumberKeyboard

class PinActivity: AppCompatActivity(), ShuffleNumberKeyboard.KeypadListener {

    private val binding: ActivityPinBinding by lazy {
        ActivityPinBinding.inflate(layoutInflater)
    }

    private val viewModel: PinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.shuffleKeyboard.setKeypadListener(this)

        viewModel.messageLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClickNumber(num: String) {
        viewModel.input(num)
    }

    override fun onClickDelete() {
        viewModel.delete()
    }

    override fun onClickDone() {
        viewModel.done()
    }
}