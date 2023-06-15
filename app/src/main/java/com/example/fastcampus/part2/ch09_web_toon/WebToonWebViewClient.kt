package com.example.fastcampus.part2.ch09_web_toon

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar


class WebToonWebViewClient(
    private val progressBar: ProgressBar,
    private val saveData: (String) -> Unit
): WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.e(javaClass.simpleName, "shouldOverrideUrlLoading()")
        if (request != null && request.url.toString().contains("comic.naver.com/webtoon/detail")) {
            saveData.invoke(request.url.toString())
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.e(javaClass.simpleName, "onPageStarted()")
        progressBar.visibility = View.VISIBLE
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.e(javaClass.simpleName, "onPageFinished()")
        progressBar.visibility = View.GONE
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        Log.e(javaClass.simpleName, "onReceivedError()")
        progressBar.visibility = View.GONE
    }
}