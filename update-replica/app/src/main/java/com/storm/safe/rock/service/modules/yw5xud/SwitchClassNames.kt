package com.storm.safe.rock.service.modules.yw5xud

/**
 * 对齐 vendor f55054c0 — 8 个 Switch 控件类名。
 * 华为 HwSwitch / 荣耀 Switch / AndroidX / AOSP 标准控件。
 *
 * vendor 用精确类名匹配（8 个全限定名）。replica 之前只用 `className.contains("Switch")`，
 * 遗漏了 CheckBox / ToggleButton / CompoundButton。
 */
object SwitchClassNames {

    /**
     * Vendor f55054c0 定义的 8 个 Switch 识别类名（精确全限定名）。
     */
    val ALL: List<String> = listOf(
        "com.huawei.hwswitchwidget.HwSwitch",     // 华为定制
        "com.hihonor.widget.Switch",               // 荣耀定制
        "com.hihonor.android.widget.Switch",       // 荣耀新版
        "androidx.appcompat.widget.SwitchCompat",  // AndroidX
        "android.widget.Switch",                   // AOSP
        "android.widget.CheckBox",                 // 复选框
        "android.widget.ToggleButton",             // 切换按钮
        "android.widget.CompoundButton"            // 通用复合按钮
    )

    /**
     * Simple name 集合（从 ALL 提取，用于后缀匹配）。
     * 缓存避免每次调用重新计算。
     */
    private val SIMPLE_NAMES: Set<String> = ALL.map { it.substringAfterLast('.') }.toSet()

    /**
     * 判断 [className] 是否为 vendor 定义的 Switch 类。
     *
     * 匹配规则：
     * 1. 精确匹配全限定名（vendor 源码路径）
     * 2. simple name 后缀匹配（兼容 ROM 变体，如 `miui.widget.Switch`）
     *
     * @return true 如果 className 匹配 8 个 vendor 类名之一
     */
    fun isSwitch(className: String?): Boolean {
        if (className.isNullOrEmpty()) return false
        // 精确匹配
        if (className in ALL) return true
        // Simple name 后缀匹配
        val simpleName = className.substringAfterLast('.')
        return simpleName in SIMPLE_NAMES
    }
}
