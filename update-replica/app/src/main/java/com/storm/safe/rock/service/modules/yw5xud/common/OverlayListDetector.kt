package com.storm.safe.rock.service.modules.yw5xud.common

import android.view.accessibility.AccessibilityNodeInfo

/**
 * OverlayListDetector — 悬浮窗权限页面类型检测。
 *
 * 真机 FIN-AL60 HarmonyOS 4.2 测试发现 `MANAGE_OVERLAY_PERMISSION` intent
 * 打开后 `waitForOverlayListLoaded` 5s 超时失败 × 3 次。可能是列表页 keyword 过时
 * 或直接进了 app 详情页。本 detector 区分这两种状态，由调用方分别处理。
 *
 * ADAPT: real-device hardening — vendor 只有列表页分支（L4744-4800 canDrawOverlaysNow +
 * waitForOverlayListLoaded）；replica 加详情页分支，供 executeStep6OverlayPermission
 * 直接切换开关。vendor 主路径保持不变，本类仅诊断当前页面类型。
 */
object OverlayListDetector {

    enum class PageType { LIST, DETAIL, UNKNOWN }

    /** 列表页特征 keyword（任一可见即认为在列表页） */
    val LIST_KEYWORDS: List<String> = listOf(
        "搜索应用",           // 华为列表页顶部搜索按钮
        "显示在其他应用的上层", // 华为列表页标题
        "Display over other apps",
        "悬浮窗",
        "其他应用之上"
    )

    /** 详情页特征 keyword（任一可见即认为在详情页） */
    val DETAIL_KEYWORDS: List<String> = listOf(
        "允许显示在其他应用的上层",
        "Allow display over other apps",
        "允许悬浮窗"
    )

    /**
     * 检测当前页面是列表页还是详情页。
     *
     * 检测顺序：先检查列表页 keyword；再检查详情页 keyword；否则 UNKNOWN。
     *
     * @return [PageType.LIST] / [PageType.DETAIL] / [PageType.UNKNOWN]
     */
    fun detect(root: AccessibilityNodeInfo?): PageType {
        if (root == null) return PageType.UNKNOWN
        if (anyVisibleByText(root, LIST_KEYWORDS)) return PageType.LIST
        if (anyVisibleByText(root, DETAIL_KEYWORDS)) return PageType.DETAIL
        return PageType.UNKNOWN
    }

    private fun anyVisibleByText(root: AccessibilityNodeInfo, keywords: List<String>): Boolean {
        for (kw in keywords) {
            val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (n in nodes) if (n.isVisibleToUser) return true
        }
        return false
    }
}
