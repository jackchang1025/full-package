package com.storm.safe.rock.service.modules.yw5xud

/**
 * OPPO 电池菜单路径常量。
 *
 * 对齐文档"权限 2 — 电池优化豁免"。`#` 分隔符驱动多级菜单导航。
 * vendor `OppoStepsSimplified$mOppo` / `$mRealme` / `$mOnePlus` 内部使用。
 */
object OppoBatteryPaths {
    /** OPPO/OPLUS 4 级菜单(scrollLimit=5) */
    const val OPPO_OPLUS_PATH = "更多设置#高级设置#智能省电场景#更多"

    /** Realme SDK ≤ 34 3 级菜单(scrollLimit=5) */
    const val REALME_LEGACY_PATH = "更多设置#高级设置#更多"

    /** OnePlus SDK ≤ 34 2 级菜单(scrollLimit=3) */
    const val ONEPLUS_LEGACY_PATH = "高级设置#更多设置"

    /** 通用:自启动管理入口(SDK≥35) */
    const val AUTOSTART_ENTRY_PATH = "自启动#自启动管理"

    /** 通用确认对话框 */
    const val CONFIRM_PATH = "允许#确定"

    /** 电池相关 UI 目标文本(OPPO 路径) */
    val OPPO_UI_TEXTS = listOf(
        "电池", "更多设置", "高级设置", "智能省电场景", "更多",
        "睡眠待机优化", "待机耗电优化", "耗电异常优化", "不优化", "省电模式"
    )

    /** OnePlus UI 目标文本 */
    val ONEPLUS_UI_TEXTS = listOf(
        "高级设置", "更多设置", "睡眠待机优化", "耗电异常优化", "不优化",
        "省电模式", "均衡模式", "电池模式", "省电设置", "自动进入省电模式",
        "电池优化", "耗电管理", "立即关闭", "立即开启"
    )

    /** Realme UI 目标文本(含 legacy 省电模式优化项) */
    val REALME_UI_TEXTS = listOf(
        "电池", "省电模式", "省电设置", "智能省电场景", "自动进入省电模式",
        "睡眠待机优化", "更多设置", "高级设置", "更多",
        "耗电异常优化", "不优化", "待机优化", "关闭",
        "充电至 90% 自动关闭", "设定自动开启电量", "超级省电模式",
        "省电模式优化项", "降低屏幕亮度", "自动息屏时间调整为15秒",
        "停用后台同步功能", "降低屏幕刷新率"
    )
}
