package com.storm.safe.rock.p000

import android.webkit.WebView
import android.widget.FrameLayout
import com.storm.safe.rock.iuzxujjtqev

// JADX: p000/ne1.java (39 LOC)
class WebViewManager(val activity: iuzxujjtqev) {

    var webView: WebView? = null
        private set

    var webViewContainer: FrameLayout? = null
    var fullScreenVideoContainer: FrameLayout? = null

    @Suppress("SetJavaScriptEnabled")
    fun initialize(webView: WebView) {
        this.webView = webView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webView.webViewClient = MainWebViewClient(this)
        webView.webChromeClient = MainWebChromeClient(this)
        webView.addJavascriptInterface(MainJsBridge(this), "Android")
    }
}
