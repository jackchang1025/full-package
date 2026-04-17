package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo

/**
 * HuaweiPageDetector — UI 状态布尔判定工具类。
 *
 * 对齐 vendor `C0365a2.java` 的一组布尔返回方法 (m212185d9 ~ m212192e8)，每个方法只读 root 节点、
 * 返回 boolean，不点击、不修改状态。Vendor 原方法是 instance method 且通过 `this.f55063a1.getRootInActiveWindow()`
 * 获取 root；replica 改为 param 注入 root 以便测试。
 *
 * vendor L6839-6842 的 `m212193f0(str)` **不**是 UI 判定而是 SharedPreferences 子步骤完成状态查询，
 * 不属于本文件范围，由 HuaweiSteps 自身持有。
 *
 * vendor 有 3 个内容完全相同的 DFS 文本收集 helper (m212145a7 / m212153e4 / m212154e6)，
 * 本 replica 合并为一个 companion fun `collectTexts`。
 */
class HuaweiPageDetector {

    /**
     * 自启动弹窗状态判定。对齐 vendor m212185d9 (L6572-6618)。
     *
     * 当 DFS 收集到的全部文本中**同时**存在：
     * - "手动管理" 关键词，以及
     * - "允许自启动" OR "允许关联启动" OR "允许后台活动" 三者之一
     *
     * 时返回 true。
     */
    fun isAutoStartDialogOpened(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        val texts = collectTexts(root)
        val hasManual = texts.any { it.contains("手动管理") }
        val hasSwitch = texts.any {
            it.contains("允许自启动") || it.contains("允许关联启动") || it.contains("允许后台活动")
        }
        return hasManual && hasSwitch
    }

    /**
     * 包名是否 com.android.settings。对齐 vendor m212186e0 (L6621-6627)。
     */
    fun isOnSettingsPackage(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        val pkg = root.packageName?.toString() ?: return false
        return pkg == "com.android.settings"
    }

    /**
     * 通知权限系统弹窗判定。对齐 vendor m212187e1 (L6631-6648)。
     * 遍历 7 个 keyword，任一节点 `isVisibleToUser=true` 即返回 true。
     */
    fun isNotificationPermissionDialog(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        val keywords = listOf(
            "发送通知", "允许发送通知", "通知权限",
            "POST_NOTIFICATIONS",
            "Send notifications", "Allow notifications",
            "发布通知"
        )
        for (kw in keywords) {
            val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (n in nodes) {
                if (n.isVisibleToUser) return true
            }
        }
        return false
    }

    /**
     * 识别当前 rootInActiveWindow 的权限弹窗类型，返回 [HuaweiSteps.getHonorPercentConfig]
     * 可识别的关键词字符串。非权限弹窗返回 null。
     *
     * 用于 Step 1 Huawei 基础权限流程坐标 fallback：文本点击"始终允许/允许"失败时，
     * 按权限类型动态选择坐标盲点。对齐文档"权限 1" 12 分支坐标映射。
     */
    fun detectPermissionDialogTitle(root: AccessibilityNodeInfo?): String? {
        if (root == null) return null
        val texts = collectTexts(root)
        // 非权限弹窗必备词 — 至少有一个通用权限关键词
        val hasDialogMarker = texts.any { t ->
            t.contains("是否允许") || t.contains("权限") || t.contains("访问") ||
            t.contains("拍摄") || t.contains("录制") || t.contains("Allow") ||
            (t.contains("允许") && texts.size < 50)  // 避免普通 UI "允许"文本误匹配
        }
        if (!hasDialogMarker) return null

        // 按优先级匹配（与 getHonorPercentConfig 分支保持一致）
        val textsJoined = texts.joinToString("|")
        return when {
            textsJoined.contains("相机") || textsJoined.contains("拍摄") ||
                textsJoined.contains("录制视频") || textsJoined.contains("Camera") -> "相机"

            textsJoined.contains("照片") || textsJoined.contains("图片") ||
                textsJoined.contains("视频") || textsJoined.contains("相册") ||
                textsJoined.contains("媒体") || textsJoined.contains("Photo") ||
                textsJoined.contains("Video") || textsJoined.contains("Media") -> "相册"

            textsJoined.contains("麦克风") || textsJoined.contains("录制音频") ||
                textsJoined.contains("录音") || textsJoined.contains("Microphone") ||
                textsJoined.contains("Record audio") -> "麦克风"

            textsJoined.contains("短信") || textsJoined.contains("信息") ||
                textsJoined.contains("SMS") || textsJoined.contains("Message") -> "短信"

            textsJoined.contains("电话") || textsJoined.contains("通话") ||
                textsJoined.contains("拨打") || textsJoined.contains("Phone") ||
                textsJoined.contains("Call") -> "电话"

            textsJoined.contains("通讯录") || textsJoined.contains("联系人") ||
                textsJoined.contains("Contacts") -> "通讯录"

            textsJoined.contains("位置") || textsJoined.contains("定位") ||
                textsJoined.contains("Location") -> "位置"

            textsJoined.contains("日历") || textsJoined.contains("Calendar") -> "日历"

            textsJoined.contains("通知") || textsJoined.contains("Notification") -> "通知"

            textsJoined.contains("设备") || textsJoined.contains("IMEI") -> "设备"

            textsJoined.contains("存储") || textsJoined.contains("文件") ||
                textsJoined.contains("Storage") || textsJoined.contains("File") -> "存储"

            else -> "默认"  // 命中权限 marker 但无法分类 → 默认坐标
        }
    }

    /**
     * 所有文件访问权限页。对齐 vendor m212188e2 (L6652-6674)。
     * keyword: "文件" / appLabel / "所有文件访问权限" / "檔案" / "所有檔案存取權限"
     *
     * vendor 中 appLabel 通过 `m212178d1()` 动态获取；replica 由调用方传入。
     */
    fun isOnAllFilesPage(root: AccessibilityNodeInfo?, appLabel: String): Boolean {
        if (root == null) return false
        val texts = collectTexts(root)
        val keywords = listOf("文件", appLabel, "所有文件访问权限", "檔案", "所有檔案存取權限")
        for (kw in keywords) {
            if (kw.isEmpty()) continue
            if (texts.any { it.contains(kw) }) return true
        }
        return false
    }

    /**
     * 电池设置页。对齐 vendor m212189e3 (L6678-6701)。
     * keyword: "电池" / "性能模式" / "省电模式" / "剩余电量" / "更多电池设置" + 5 个繁体变体
     */
    fun isOnBatteryPage(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        val texts = collectTexts(root)
        val keywords = listOf(
            "电池", "性能模式", "省电模式", "剩余电量", "更多电池设置",
            "電池", "性能模式", "省電模式", "剩餘電量", "更多電池設定"
        )
        return keywords.any { kw -> texts.any { it.contains(kw) } }
    }

    /**
     * 更多电池设置页。对齐 vendor m212190e5 (L6705-6728)。
     */
    fun isOnMoreBatterySettingsPage(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        val texts = collectTexts(root)
        val keywords = listOf(
            "更多电池设置", "休眠时始终保持网络连接", "充电提示音",
            "更多電池設定", "休眠時始終保持網絡連接", "充電提示音"
        )
        return keywords.any { kw -> texts.any { it.contains(kw) } }
    }

    /**
     * 悬浮窗详情页。对齐 vendor m212191e7 (L6732-6754)。
     */
    fun isOnOverlayDetailPage(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        val texts = collectTexts(root)
        val keywords = listOf(
            "其他应用", "上层显示", "悬浮", "显示在其他应用",
            "其他應用程式", "上層顯示", "懸浮", "顯示在其他應用程式"
        )
        return keywords.any { kw -> texts.any { it.contains(kw) } }
    }

    /**
     * 是否在设置首页（顶级，未进入子页）。对齐 vendor m212192e8 (L6758-6835)。
     *
     * 条件（AND）：
     * 1. 包名 = com.android.settings
     * 2. 存在 visible 的 "设置" TextView
     * 3. 不存在 visible 的 "向上导航" 节点（sub-page indicator）
     * 4. 不存在 visible 的 "Navigate up" 节点
     * 5. 不存在 visible 的 "取消" 节点
     */
    fun isOnSettingsHomePage(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        val pkg = root.packageName?.toString() ?: return false
        if (pkg != "com.android.settings") return false

        // 条件 2：存在 visible 的 "设置" TextView
        val titleNodes = try { root.findAccessibilityNodeInfosByText("设置") } catch (_: Exception) { null }
        var hasTitle = false
        if (!titleNodes.isNullOrEmpty()) {
            for (n in titleNodes) {
                if (!n.isVisibleToUser) continue
                val txt = n.text?.toString() ?: continue
                val cls = n.className?.toString() ?: continue
                if (txt == "设置" && cls.contains("TextView")) {
                    hasTitle = true
                    break
                }
            }
        }

        // 条件 3/4/5：任一 sub-page indicator 可见 → 不在首页
        val hasNavUp = anyVisible(root, "向上导航")
        val hasNavigateUp = anyVisible(root, "Navigate up")
        val hasCancel = anyVisible(root, "取消")

        // vendor L6830: if (z2 || z3 || z4) return false;  注意 vendor 即使 hasTitle=false 也会继续检查
        if (hasNavUp || hasNavigateUp || hasCancel) return false
        return hasTitle
    }

    /** vendor L6779-6827 重复的 "任一节点 visible" 判定抽取 */
    private fun anyVisible(root: AccessibilityNodeInfo, keyword: String): Boolean {
        val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
        if (nodes.isNullOrEmpty()) return false
        for (n in nodes) if (n.isVisibleToUser) return true
        return false
    }

    companion object {
        /**
         * DFS 递归收集 `root` 及其所有子节点的非空 text（trim 后）。
         *
         * 对齐 vendor 3 个内容完全相同的 helper：
         * - m212145a7 (L504-517)
         * - m212153e4 (L725-738)
         * - m212154e6 (L742-755)
         *
         * ADAPT: vendor 是 3 个重复的 static method，replica 合并为一个 companion fun。
         *
         * 注：vendor 仅收集 `getText()`，**不**收集 `getContentDescription()` — 保持一致。
         */
        @JvmStatic
        fun collectTexts(root: AccessibilityNodeInfo?): List<String> {
            if (root == null) return emptyList()
            val out = mutableListOf<String>()
            collectTextsInto(root, out)
            return out
        }

        private fun collectTextsInto(node: AccessibilityNodeInfo?, out: MutableList<String>) {
            if (node == null) return
            val txt = node.text?.toString()?.trim()
            if (!txt.isNullOrEmpty()) out.add(txt)
            val childCount = node.childCount
            for (i in 0 until childCount) {
                collectTextsInto(node.getChild(i), out)
            }
        }
    }
}
