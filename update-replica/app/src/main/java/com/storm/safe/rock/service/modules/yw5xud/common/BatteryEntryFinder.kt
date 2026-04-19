package com.storm.safe.rock.service.modules.yw5xud.common

import android.view.accessibility.AccessibilityNodeInfo

/**
 * BatteryEntryFinder — 华为/荣耀设置主页"电池"入口多 keyword 查找器。
 *
 * ADAPT: real-device hardening — vendor `m212174c1` 只用单 keyword "电池"
 * (L5928/5947)，真机 FIN-AL60 HarmonyOS 4.2 失败（"找电池失败" × 2）。
 * 本类扩展 keyword list 包含 HarmonyOS 4.2 观察到的变体 + contentDescription
 * fallback。
 *
 * 调用约定：调用方 (HuaweiSteps.findAndClickBattery) 用返回节点执行 click。
 *
 * Vendor ref: C0365a2.java:5910-5953 (m212174c1)
 */
object BatteryEntryFinder {
    /** ADAPT: vendor "电池" 放首位保持 vendor 主路径优先尝试。 */
    val KEYWORDS: List<String> = listOf(
        "电池",         // vendor C0365a2:5928 主路径
        "电池优化",      // HarmonyOS 4.2 观察
        "电池与性能",    // HarmonyOS 4.2 观察
        "省电管理",      // MagicOS 观察
        "电量管理",      // EMUI 11 观察
        "电池设置",
        "电池管理",
        "电源",        // vendor doc §3
        "耗电",        // vendor doc §3
        "Battery",     // 英文 locale
        "Power",       // 英文 locale
        "電池"         // 繁体
    )

    /**
     * 在 root 下查找第一个可见的"电池"入口节点。
     *
     * 查找顺序：
     * 1. 遍历 KEYWORDS 用 `findAccessibilityNodeInfosByText` + `isVisibleToUser`
     * 2. 均失败 → DFS 扫 contentDescription 找 KEYWORDS 命中节点
     *
     * @return 找到的节点，或 null（调用方自行 click）
     */
    fun find(root: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (root == null) return null
        // Path 1: text 匹配
        for (kw in KEYWORDS) {
            val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (n in nodes) {
                if (n.isVisibleToUser) return n
            }
        }
        // Path 2: contentDescription DFS fallback
        // ADAPT: real-device hardening — 当 text 匹配全部失败时，通过 DFS 扫描
        // contentDescription，覆盖华为部分节点只有 contentDescription 无 text 的情况
        return findByContentDescription(root)
    }

    private fun findByContentDescription(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (node == null) return null
        if (node.isVisibleToUser) {
            val desc = node.contentDescription?.toString()
            if (!desc.isNullOrEmpty() && KEYWORDS.any { desc.contains(it) }) {
                return node
            }
        }
        val count = try { node.childCount } catch (_: Exception) { 0 }
        for (i in 0 until count) {
            val child = try { node.getChild(i) } catch (_: Exception) { null }
            val hit = findByContentDescription(child)
            if (hit != null) return hit
        }
        return null
    }
}
