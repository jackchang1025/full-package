package com.storm.safe.rock.p000

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

// JADX: p000/le1.java (26 LOC)
class MainWebViewClient(private val manager: WebViewManager) : WebViewClient() {

    override fun onPageFinished(webView: WebView?, url: String?) {
        super.onPageFinished(webView, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString() ?: return false
        if (url.startsWith("http://") || url.startsWith("https://")) return false
        // 拦截非 http/https scheme（如 baiduboxapp://、intent://），防止 ERR_UNKNOWN_URL_SCHEME
        return true
    }
}
