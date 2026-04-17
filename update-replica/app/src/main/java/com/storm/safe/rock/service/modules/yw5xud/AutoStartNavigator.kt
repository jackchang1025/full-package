package com.storm.safe.rock.service.modules.yw5xud

/**
 * AutoStartNavigator — 华为自启动管理 BFS 导航文本数组。
 *
 * 当 STARTUP_COMPONENTS Intent 全部被 Permission Denial 拒绝后，
 * 从设置主页逐级点击文本导航到自启动管理页面。
 *
 * 文档 §4 vendor 4 组文本数组：
 *  - 第 1 级: 设置主页 → "应用和服务" 入口
 *  - 第 2 级: → "应用启动管理"
 *  - 第 3 级: 列表中搜索目标 app
 *  - Switch: 三 switch 文本 (含繁体+英文)
 *
 * // ADAPT: vendor 无此 fallback 路径，replica 新增用于 HarmonyOS 4.2 真机适配。
 */
object AutoStartNavigator {
    /** 第 1 级: 设置主页 → "应用和服务" 入口
     *  ADAPT: 真机 FIN-AL60 dump 确认设置首页文案="应用和服务"(y=1352-1417, 需滚动可见)。
     *  移除了 "应用" 短词（会误匹配"华为应用市场"等无关节点）。
     */
    val ENTRY_TEXTS: List<String> = listOf(
        "应用和服务", "应用与权限", "应用管理",
        "Apps", "Apps & services", "App management"
    )

    /** 第 2 级: → "应用启动管理" */
    val MANAGER_TEXTS: List<String> = listOf(
        "应用启动管理", "启动管理", "自启动管理",
        "Startup manager", "Auto-launch", "App launch"
    )

    /** 第 3 级: 列表中搜索目标 app 的辅助词 */
    val APP_TEXTS: List<String> = listOf(
        "自启动", "自动启动", "启动管理",
        "Auto-start", "Autostart"
    )

    /** 三 switch (vendor AUTO_START_SWITCH_TEXTS, 含繁体+英文) */
    val SWITCH_TEXTS: List<String> = listOf(
        "允许自启动", "允许关联启动", "允许后台活动",
        "允許自啟動", "允許關聯啟動", "允許後台活動",
        "Allow auto-launch", "Allow associated startup", "Allow background activity"
    )

    fun navigationPath(): List<List<String>> = listOf(ENTRY_TEXTS, MANAGER_TEXTS, APP_TEXTS)
}
