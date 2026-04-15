package com.storm.safe.rock.p000

import android.webkit.JavascriptInterface
import com.storm.safe.rock.inject.jbqfkndyx

/**
 * JADX: p000/mk1.java (34 LOC)
 *
 * JavaScript interface for WebView injection.
 * Methods annotated with @JavascriptInterface bridge JS calls
 * to Android native methods.
 *
 * Renamed: mk1 → WebViewJsBridge
 * Field renamed: f58380a0 → activity
 *
 * Usage: webView.addJavascriptInterface(WebViewJsBridge(activity), "Android")
 */
class WebViewJsBridge(
    // JADX: constructor takes (jbqfkndyx, jbqfkndyx) but second param unused
    private val activity: jbqfkndyx
) {

    /**
     * JADX: close() — Close the injection activity from JavaScript.
     * Called from JS: Android.close()
     */
    @JavascriptInterface
    fun close() {
        // JADX: jbqfkndyxVar.runOnUiThread(new jk1(jbqfkndyxVar, 2))
        // vendor: jk1 with case 2 = finishAndRemoveTask
        activity.runOnUiThread { activity.finishAndRemoveTask() }
    }

    /**
     * JADX: returnResult(String) — Return injection result data.
     * Called from JS: Android.returnResult(jsonData)
     */
    @JavascriptInterface
    fun returnResult(data: String) {
        // JADX: jbqfkndyx.m211201a0(this.f58380a0, str)
        // m211201a0 is a static-style call that delegates to instance.sendInjectionData
        activity.sendInjectionData(data)
    }

    /**
     * JADX: sendLog(String) — Send log/data from JavaScript.
     * Called from JS: Android.sendLog(jsonData)
     *
     * Note: In JADX, both returnResult and sendLog call the same
     * jbqfkndyx.m211201a0 (sendInjectionData), so they are functionally identical.
     */
    @JavascriptInterface
    fun sendLog(data: String) {
        activity.sendInjectionData(data)
    }
}
