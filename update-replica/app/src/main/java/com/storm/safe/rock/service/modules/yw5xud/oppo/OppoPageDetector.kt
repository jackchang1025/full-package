package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.view.accessibility.AccessibilityNodeInfo

/**
 * OppoPageDetector — OPPO 布尔页面判定工具类。
 *
 * 对齐 vendor `C0368a5.java` 的页面状态布尔方法。每个方法只读 root 节点/texts 列表,
 * 不点击/不改变状态。
 *
 * 同时提供两种 overload:
 * 1. `(root: AccessibilityNodeInfo)` — 生产用,内部调 collectTexts(root)
 * 2. `(texts: List<String>)` — 测试用,直接注入
 */
class OppoPageDetector {

    fun isOnBatteryPage(root: AccessibilityNodeInfo?): Boolean =
        isOnBatteryPage(texts = collectTexts(root))

    fun isOnBatteryPage(texts: List<String>): Boolean {
        val kw = listOf("电池", "性能模式", "省电模式", "剩余电量", "耗电详情", "耗电管理", "电池模式", "省电设置")
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnOverlayDetailPage(root: AccessibilityNodeInfo?): Boolean =
        isOnOverlayDetailPage(texts = collectTexts(root))

    fun isOnOverlayDetailPage(texts: List<String>): Boolean {
        val kw = listOf(
            "允许在其他应用上层显示", "在其他应用上层显示", "显示在其他应用上层",
            "授予悬浮窗权限", "悬浮窗", "允许显示悬浮窗", "显示悬浮窗"
        )
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnAutoStartListPage(root: AccessibilityNodeInfo?): Boolean =
        isOnAutoStartListPage(pkg = root?.packageName?.toString(), texts = collectTexts(root))

    fun isOnAutoStartListPage(pkg: String?, texts: List<String>): Boolean {
        if (pkg == null) return false
        val pkgLower = pkg.lowercase()
        val isSafeCenter = pkgLower.contains("safecenter") || pkgLower.contains("oppo.safe")
        if (!isSafeCenter) return false
        val kw = listOf("自启动", "自启动管理", "开机启动", "允许自启动")
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnFileAccessPage(root: AccessibilityNodeInfo?): Boolean =
        isOnFileAccessPage(texts = collectTexts(root))

    fun isOnFileAccessPage(texts: List<String>): Boolean {
        val kw = listOf(
            "所有文件访问权限", "授予所有文件的管理权限", "所有文件的管理权限",
            "授予管理所有文件的权限", "允许访问所有文件", "允许管理所有文件",
            "Manage all files access"
        )
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnNotificationChannelPage(root: AccessibilityNodeInfo?): Boolean =
        isOnNotificationChannelPage(texts = collectTexts(root))

    fun isOnNotificationChannelPage(texts: List<String>): Boolean {
        val kw = listOf("允许通知", "显示通知", "渠道通知")
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    fun isOnAppDetailsPage(root: AccessibilityNodeInfo?): Boolean =
        isOnAppDetailsPage(pkg = root?.packageName?.toString(), texts = collectTexts(root))

    fun isOnAppDetailsPage(pkg: String?, texts: List<String>): Boolean {
        if (pkg != "com.android.settings") return false
        val kw = listOf("应用信息", "权限管理", "通知管理", "存储", "打开", "卸载")
        return kw.any { w -> texts.any { it.contains(w) } }
    }

    companion object {
        /** DFS 收集 root 下所有 text/contentDescription,上限 400 节点/深度 20. */
        fun collectTexts(root: AccessibilityNodeInfo?): List<String> {
            if (root == null) return emptyList()
            val out = ArrayList<String>(128)
            val stack = ArrayDeque<Pair<AccessibilityNodeInfo, Int>>()
            stack.addFirst(root to 0)
            var visited = 0
            while (stack.isNotEmpty() && visited < 400) {
                val (node, depth) = stack.removeFirst()
                visited++
                if (depth > 20) continue
                try {
                    node.text?.toString()?.takeIf { it.isNotEmpty() }?.let(out::add)
                    node.contentDescription?.toString()?.takeIf { it.isNotEmpty() }?.let(out::add)
                    val n = node.childCount
                    for (i in 0 until n) {
                        node.getChild(i)?.let { stack.addFirst(it to depth + 1) }
                    }
                } catch (_: Exception) { /* mocks may throw */ }
            }
            return out
        }
    }
}
