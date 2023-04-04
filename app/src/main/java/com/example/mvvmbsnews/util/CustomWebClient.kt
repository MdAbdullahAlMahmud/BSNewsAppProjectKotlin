package com.example.mvvmbsnews.util

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class CustomWebClient(progressBar : ProgressBar) : WebViewClient() {
    var  progressBarV : ProgressBar

    init {
        progressBarV =  progressBar
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        progressBarV.visibility= View.VISIBLE


    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBarV.visibility= View.GONE

    }
}