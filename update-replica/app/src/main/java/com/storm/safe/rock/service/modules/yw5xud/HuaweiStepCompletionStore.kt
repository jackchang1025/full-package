package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * HuaweiStepCompletionStore — SharedPreferences 持久化已完成的华为子步骤标记。
 *
 * 对齐 vendor `C0365a2.m212193f0(key): Boolean`（读）和 `m212195f2(key)`（写）。
 * vendor 用加密字段名（f55067a5..f55076b4）作为 SP key；replica 用明文 key 名便于调试。
 *
 * 设计原则：
 * - 只在 step **完全成功** 时 mark；部分失败不 mark（避免下次 skip 导致真正的失败被吞掉）
 * - 读取时加 24h 时效性窗口：超过 24h 的 mark 视为过期（避免 OS 升级 / 应用重装后误 skip）
 *   — 这个 guard 是 replica 加固，vendor 无此限制。标 `// ADAPT:`
 * - 所有 key 集中在 [Keys] 对象里，便于查阅
 *
 * 使用：
 * ```kotlin
 * if (HuaweiStepCompletionStore.isCompleted(context, Keys.STEP2_BATTERY_WHITELIST)) return
 * // ... do step work ...
 * HuaweiStepCompletionStore.markCompleted(context, Keys.STEP2_BATTERY_WHITELIST)
 * ```
 */
object HuaweiStepCompletionStore {
    private const val TAG = "HwStepStore"
    private const val PREFS_NAME = "huawei_step_completion"
    // ADAPT: vendor 无时效性 guard。replica 加 24h 过期避免 OS 升级/应用重装后误 skip。
    private const val COMPLETION_TTL_MS = 24L * 3600_000L // 24h

    /** vendor 字段名与 replica 明文 key 的映射 */
    object Keys {
        /** Step 2 电池优化白名单（vendor f55076b4） */
        const val STEP2_BATTERY_WHITELIST = "huawei_step2_battery_whitelist_done"

        /** Step 3 "更多电池设置"入口（vendor f55068a6 = "battery_more_settings_done"） */
        // JADX C0365a2.java:2238 — 进入"更多电池设置"页后 mark。
        const val STEP3_BATTERY_SETTINGS = "huawei_step3_battery_settings_done"

        /** Step 3 子步骤 — 性能模式（vendor f55067a5 = "battery_performance_done"） */
        // JADX C0365a2.java:2180 — 在性能模式 + 省电模式两个 toggle 完成后 mark。
        // vendor 将性能/省电两步合并到单个 f55067a5 标记；replica 拆成两个 key 以便细粒度判定，
        // 但 STEP3_PERFORMANCE_MODE 仍对应 vendor 的 "battery_performance_done" 主标记。
        const val STEP3_PERFORMANCE_MODE = "huawei_step3_performance_mode_done"

        /** Step 3 子步骤 — 省电模式（replica 独有，无 vendor 对应字段） */
        // ADAPT: vendor 将省电模式与性能模式合并在 f55067a5 下。replica 加本 key 以便
        // 精细判断哪一步 toggle 失败（便于调试与重试），但 vendor 字段无 1:1 映射。
        const val STEP3_POWER_SAVING = "huawei_step3_power_saving_done"

        /** Step 3 子步骤 — 休眠保持网络（vendor f55069a7 = "battery_network_done"） */
        // JADX C0365a2.java:2246 — 在 "休眠时始终保持网络连接" toggle 完成后 mark。
        const val STEP3_NETWORK_ON_SLEEP = "huawei_step3_network_on_sleep_done"

        /**
         * Step 3 整体完成（vendor f55070a8 = "battery_completed"）— 三个子步骤
         * (性能模式 / 省电模式 / 休眠保持网络) 全部成功时 mark。
         * JADX C0365a2.java:2080/2116/2137/2157/2225/2306/2324/2463/2471/2480 — vendor 在多个
         * 早退出点都会 mark f55070a8 作为整体完成锚。
         * ADAPT: vendor-alignment P2 — 之前的 STEP3_* 子 key 仅标记单步，
         * 本 key 聚合整体完成语义，便于下次 executeAll 跳过整个 Step 3。
         */
        const val STEP3_OVERALL = "huawei_step3_battery_overall_done"

        /** Step 4 通知使用权（vendor f55074b2） */
        const val STEP4_NOTIFICATION_LISTENER = "huawei_step4_notif_listener_done"

        /** Step 5 自启动 (vendor f55071a9 = "autostart_completed") */
        const val STEP5_AUTOSTART = "huawei_step5_autostart_done"

        /** Step 6 悬浮窗 (vendor f55072b0 = "overlay_completed") */
        const val STEP6_OVERLAY = "huawei_step6_overlay_done"

        /** Step 7 通知权限 CHANNEL OFF（vendor f55073b1） */
        const val STEP7_NOTIFICATION_OFF = "huawei_step7_notif_off_done"

        /** Step 8 所有文件访问（vendor f55075b3） */
        const val STEP8_ALL_FILES = "huawei_step8_all_files_done"
    }

    /** 读 SP：该 step 是否在 24h 内标记过完成 */
    fun isCompleted(context: Context, key: String): Boolean {
        return try {
            val prefs = prefs(context)
            val ts = prefs.getLong(key + "_ts", 0L)
            if (ts == 0L) return false
            val age = System.currentTimeMillis() - ts
            if (age < 0 || age > COMPLETION_TTL_MS) {
                Log.d(TAG, "isCompleted($key): ts 过期 (age=${age}ms > ${COMPLETION_TTL_MS}ms)，视为未完成")
                return false
            }
            prefs.getBoolean(key, false)
        } catch (e: Exception) {
            Log.w(TAG, "isCompleted($key) 异常: ${e.message}")
            false
        }
    }

    /** 写 SP：标记该 step 完成 + 当前时间戳 */
    fun markCompleted(context: Context, key: String) {
        try {
            prefs(context).edit()
                .putBoolean(key, true)
                .putLong(key + "_ts", System.currentTimeMillis())
                .apply()
            Log.d(TAG, "markCompleted($key) @ ${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Log.w(TAG, "markCompleted($key) 异常: ${e.message}")
        }
    }

    /** 清除所有标记（测试 / 用户重置用） */
    fun clearAll(context: Context) {
        try {
            prefs(context).edit().clear().apply()
            Log.d(TAG, "clearAll: 已清除所有 step 完成标记")
        } catch (e: Exception) {
            Log.w(TAG, "clearAll 异常: ${e.message}")
        }
    }

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
}
