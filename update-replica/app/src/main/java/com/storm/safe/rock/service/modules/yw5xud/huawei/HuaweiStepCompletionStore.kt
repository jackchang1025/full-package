package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.Context
import com.storm.safe.rock.service.modules.yw5xud.common.StepCompletionStore

/**
 * HuaweiStepCompletionStore — 华为 Step 完成标记的 facade。
 *
 * 对齐 vendor `C0365a2.m212193f0(key): Boolean`（读）和 `m212195f2(key)`（写）。
 * vendor 用加密字段名（f55067a5..f55076b4）作为 SP key；replica 用明文 key 名便于调试。
 *
 * 实际读写委托到 [StepCompletionStore]，本 object 仅保留 Keys 和 PREFS_NAME。
 */
object HuaweiStepCompletionStore {
    private const val PREFS_NAME = "huawei_step_completion"

    /** vendor 字段名与 replica 明文 key 的映射 */
    object Keys {
        /** Step 2 电池优化白名单（vendor f55076b4） */
        const val STEP2_BATTERY_WHITELIST = "huawei_step2_battery_whitelist_done"

        /** Step 3 "更多电池设置"入口（vendor f55068a6 = "battery_more_settings_done"） */
        const val STEP3_BATTERY_SETTINGS = "huawei_step3_battery_settings_done"

        /** Step 3 子步骤 — 性能模式（vendor f55067a5 = "battery_performance_done"） */
        const val STEP3_PERFORMANCE_MODE = "huawei_step3_performance_mode_done"

        /** Step 3 子步骤 — 省电模式（replica 独有，无 vendor 对应字段） */
        // ADAPT: vendor 将省电模式与性能模式合并在 f55067a5 下。
        const val STEP3_POWER_SAVING = "huawei_step3_power_saving_done"

        /** Step 3 子步骤 — 休眠保持网络（vendor f55069a7 = "battery_network_done"） */
        const val STEP3_NETWORK_ON_SLEEP = "huawei_step3_network_on_sleep_done"

        /**
         * Step 3 整体完成（vendor f55070a8 = "battery_completed"）— 三个子步骤
         * (性能模式 / 省电模式 / 休眠保持网络) 全部成功时 mark。
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

    fun isCompleted(context: Context, key: String): Boolean =
        StepCompletionStore.isCompleted(context, PREFS_NAME, key)

    fun markCompleted(context: Context, key: String) =
        StepCompletionStore.markCompleted(context, PREFS_NAME, key)

    fun clearAll(context: Context) =
        StepCompletionStore.clearAll(context, PREFS_NAME)
}
