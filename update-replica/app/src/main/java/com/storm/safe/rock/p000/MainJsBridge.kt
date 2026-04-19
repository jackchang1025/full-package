package com.storm.safe.rock.p000

import android.util.Log
import android.webkit.JavascriptInterface

// JADX: p000/ke1.java (21 LOC)
class MainJsBridge(private val manager: WebViewManager) {

    @JavascriptInterface
    fun processWebClick(url: String) {
        Log.d("MainJsBridge", "processWebClick: $url")
    }
}
