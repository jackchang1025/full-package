package com.storm.safe.rock.auto.a11y

import android.view.accessibility.AccessibilityNodeInfo

const val MAX_CHILD_SIZE = 512
const val MAX_DESCENDANTS_SIZE = 4096

fun AccessibilityNodeInfo.getVid(): CharSequence? {
    val id = viewIdResourceName ?: return null
    val appId = packageName ?: return null
    if (id.startsWith(appId) && id.startsWith(":id/", appId.length)) {
        return id.subSequence(appId.length + ":id/".length, id.length)
    }
    return null
}

val AccessibilityNodeInfo.compatChecked: Boolean?
    get() = isChecked
