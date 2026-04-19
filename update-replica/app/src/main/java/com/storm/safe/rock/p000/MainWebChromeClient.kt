package com.storm.safe.rock.p000

import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout

// JADX: p000/me1.java (84 LOC)
class MainWebChromeClient(private val manager: WebViewManager) : WebChromeClient() {

    private var customViewCallback: CustomViewCallback? = null

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)
        customViewCallback = callback
        try {
            manager.webViewContainer?.visibility = View.GONE
            val fullScreenVideoContainer = manager.fullScreenVideoContainer
            fullScreenVideoContainer?.visibility = View.VISIBLE
            if (view != null && fullScreenVideoContainer != null) {
                fullScreenVideoContainer.removeAllViews()
                fullScreenVideoContainer.addView(view)
            }
            @Suppress("DEPRECATION")
            manager.activity.window.decorView.systemUiVisibility = 4102
        } catch (e: Exception) {
            Log.e("WebViewManager", "❌ 设置全屏视频失败: ${e.message}")
        }
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
        try {
            manager.webViewContainer?.visibility = View.VISIBLE
            val fullScreenVideoContainer = manager.fullScreenVideoContainer
            fullScreenVideoContainer?.visibility = View.GONE
            fullScreenVideoContainer?.removeAllViews()
            @Suppress("DEPRECATION")
            manager.activity.window.decorView.systemUiVisibility = 0
        } catch (e: Exception) {
            Log.e("WebViewManager", "❌ 退出全屏视频失败: ${e.message}")
        }
        customViewCallback?.onCustomViewHidden()
        customViewCallback = null
    }

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<android.net.Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        filePathCallback?.onReceiveValue(null)
        return true
    }
}
