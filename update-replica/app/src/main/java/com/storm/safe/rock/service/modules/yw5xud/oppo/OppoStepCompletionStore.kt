package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.content.Context
import com.storm.safe.rock.service.modules.yw5xud.common.StepCompletionStore

/**
 * OppoStepCompletionStore — OPPO Step 完成标记的 facade。
 *
 * vendor 字段:`"oppo_simplified_v6"`（v6 版本号是强特征）。
 * 9 个 SP key（文档"权限获取目的分析"表）。
 *
 * 实际读写委托到 [StepCompletionStore]，本 object 仅保留 Keys 和 PREFS_NAME。
 */
object OppoStepCompletionStore {
    // ADAPT: 暴露 PREFS_NAME 为 public const,测试可直接引用避免重复硬编码。
    //        vendor `"oppo_simplified_v6"` 是强 YARA 特征,不可改名。
    const val PREFS_NAME = "oppo_simplified_v6"

    object Keys {
        const val STEP2_BATTERY = "battery"
        const val STEP3_AUTOSTART = "autostart"
        const val STEP3_AUTOSTART_SWITCH = "autostart_switch"
        const val STEP3_AUTOSTART_BACKGROUND = "autostart_background"
        const val STEP4_OVERLAY = "overlay"
        const val STEP5_APPLIST = "applist"
        const val STEP6_FILEACCESS = "fileaccess"
        const val STEP7_NOTIFICATION = "notification"
        const val STEP8_APPLOCK = "applock"
    }

    fun isCompleted(context: Context, key: String): Boolean =
        StepCompletionStore.isCompleted(context, PREFS_NAME, key)

    fun markCompleted(context: Context, key: String) =
        StepCompletionStore.markCompleted(context, PREFS_NAME, key)

    fun clearAll(context: Context) =
        StepCompletionStore.clearAll(context, PREFS_NAME)
}
