package com.storm.safe.rock.service.modules.yw5xud.common

/**
 * 电池弹窗确认词 — 对齐 vendor C0365a2 L2616/2726 approval keyword list (14 条)。
 *
 * 华为 EMUI / HarmonyOS 请求忽略电池优化时弹出的系统对话框上可能出现的确认按钮文案。
 * 包含中英文变体，在 [HuaweiSteps.executeStep2BatteryWhitelist] 的主循环中
 * 作为优先点击词库，先于 [AllowKeywords.ALLOW] 遍历尝试。
 */
object BatteryDialogKeywords {
    val CONFIRM_TEXTS: List<String> = listOf(
        "忽略", "关闭", "不优化", "允许", "确定", "不再提醒", "知道了",
        "Ignore", "Close", "Don't optimize", "Allow", "OK", "Don't remind", "Got it"
    )
}
