package com.example.fastcampus.part2.ch09_web_toon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.fastcampus.R
import com.example.fastcampus.WEB_HISTORY
import com.example.fastcampus.databinding.FragmentWebviewBinding

class WebViewFragment(
    val position: Int, private val url: String,
) : Fragment() {

    private lateinit var binding: FragmentWebviewBinding

    var listener: OnTabLayoutNameChanged? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWebviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding) {
            webView.apply {
                webViewClient = WebToonWebViewClient(binding.progressBar) { url ->
                    activity?.getSharedPreferences(WEB_HISTORY, Context.MODE_PRIVATE)?.edit {
                        putString("tab$position", url)
                    }
                }

                settings.javaScriptEnabled = true
            }.loadUrl(url)

            backToLastButton.setOnClickListener {
                val sharedPreference =
                    activity?.getSharedPreferences(WEB_HISTORY, Context.MODE_PRIVATE)
                val url = sharedPreference?.getString("tab$position", "")
                if (url.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "마지막 저장 시점이 없습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    binding.webView.loadUrl(url)
                }
            }

            changeTabNameButton.setOnClickListener {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(getString(R.string.change_tab_name))

                    val editText = EditText(requireContext())

                    setView(editText)
                    setPositiveButton("바꾸기") { _, _ ->
                        activity?.getSharedPreferences(WEB_HISTORY, Context.MODE_PRIVATE)?.edit {
                            putString("tab${position}_name", editText.text.toString())
                            listener?.nameChanged(position, editText.text.toString())
                        }
                    }
                    setNegativeButton("취소") { dialogInterface, _ ->
                        dialogInterface.cancel()
                    }
                }.show()
            }
        }
    }

    fun canGoBack(): Boolean {
        return binding.webView.canGoBack()
    }

    fun goBack() {
        binding.webView.goBack()
    }
}

interface OnTabLayoutNameChanged {
    fun nameChanged(position: Int, name: String)
}