package com.storm.safe.rock.service.modules.yw5xud.common

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo

/**
 * AppCardMatcher — 最近任务视图中 app 卡片的多策略定位。
 *
 * ADAPT: real-device hardening — vendor `findAppCardRect` (C0365a2.m212175c2 L5956+) 只用
 * appLabel text 搜索（单一策略）。真机 FIN-AL60 HarmonyOS 4.2 appLabel="系统服务" 未在
 * 最近任务卡片中匹配成功（见 /tmp/huawei-t19e.log Step9 "未找到 APP 卡片"）。
 * 华为最近任务卡片常以 contentDescription 或 packageName 标识，而非伪装 appLabel。
 *
 * 查找策略（优先级）：
 *   1. findAccessibilityNodeInfosByText(appLabel) + isVisibleToUser → return Rect
 *   2. 1 失败 → findAccessibilityNodeInfosByText(packageName) + isVisibleToUser → return Rect
 *   3. 2 失败 → DFS 扫 contentDescription 包含 appLabel 或 packageName → return Rect
 *
 * 公开签名: findCardRect(root, appLabel, packageName): Rect?
 */
object AppCardMatcher {

    /**
     * 在最近任务 root 下查找 app 卡片的 bounding-box（屏幕坐标）。
     *
     * @param root   最近任务视图的 AccessibilityNodeInfo 根节点（可为 null）
     * @param appLabel  应用标签名（如 "系统服务"）— vendor 主路径 Strategy 1
     * @param packageName 包名（如 "com.storm.safe.rock"）— ADAPT Strategy 2
     * @return 第一个匹配节点的屏幕 Rect，或 null（所有策略均未命中）
     */
    fun findCardRect(root: AccessibilityNodeInfo?, appLabel: String, packageName: String): Rect? {
        if (root == null) return null
        // Strategy 1: appLabel text (vendor 主路径 — C0365a2 m212175c2 L5965)
        findByText(root, appLabel)?.let { return it.getBounds() }
        // Strategy 2: packageName text
        // ADAPT: real-device hardening — 伪装名不在卡片 text，改用包名匹配
        findByText(root, packageName)?.let { return it.getBounds() }
        // Strategy 3: contentDescription DFS fallback
        // ADAPT: real-device hardening — 华为最近任务卡片可能用 contentDescription 标识
        val desc = findByContentDescription(root, listOf(appLabel, packageName))
        return desc?.getBounds()
    }

    /** Strategy 1/2: text 精确包含匹配（兼容 findAccessibilityNodeInfosByText 的子串语义） */
    private fun findByText(root: AccessibilityNodeInfo, query: String): AccessibilityNodeInfo? {
        val nodes = try { root.findAccessibilityNodeInfosByText(query) } catch (_: Exception) { null }
        if (nodes.isNullOrEmpty()) return null
        for (n in nodes) if (n.isVisibleToUser) return n
        return null
    }

    /** Strategy 3: DFS 扫 contentDescription，任一 query 命中即返回 */
    private fun findByContentDescription(
        node: AccessibilityNodeInfo?,
        queries: List<String>
    ): AccessibilityNodeInfo? {
        if (node == null) return null
        if (node.isVisibleToUser) {
            val desc = node.contentDescription?.toString()
            if (!desc.isNullOrEmpty() && queries.any { desc.contains(it) }) {
                return node
            }
        }
        val count = try { node.childCount } catch (_: Exception) { 0 }
        for (i in 0 until count) {
            val child = try { node.getChild(i) } catch (_: Exception) { null }
            val hit = findByContentDescription(child, queries)
            if (hit != null) return hit
        }
        return null
    }

    private fun AccessibilityNodeInfo.getBounds(): Rect {
        val rect = Rect()
        getBoundsInScreen(rect)
        return rect
    }
}
