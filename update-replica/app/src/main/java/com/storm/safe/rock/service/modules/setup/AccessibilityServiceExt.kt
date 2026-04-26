package com.storm.safe.rock.service.modules.setup

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityNodeInfo
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

private val mainHandler = Handler(Looper.getMainLooper())

fun AccessibilityService.rootOnMainThread(timeoutMs: Long = 3_000L): AccessibilityNodeInfo? {
    if (Looper.myLooper() == Looper.getMainLooper()) return rootInActiveWindow
    val latch = CountDownLatch(1)
    var root: AccessibilityNodeInfo? = null
    mainHandler.post { root = rootInActiveWindow; latch.countDown() }
    return if (latch.await(timeoutMs, TimeUnit.MILLISECONDS)) root else null
}
