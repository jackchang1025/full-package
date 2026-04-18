package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * OppoStepCompletionStore — SharedPreferences 持久化 OPPO Step 完成标记。
 *
 * vendor 字段:`"oppo_simplified_v6"`(v6 版本号是强特征)。
 * 9 个 SP key(文档"权限获取目的分析"表)。
 *
 * ADAPT: 24h 过期 guard 是 replica 加固,避免 OS 升级 / 应用重装后误 skip。vendor 无此限制。
 */
object OppoStepCompletionStore {
    private const val TAG = "OppoStepStore"
    // ADAPT: 暴露 PREFS_NAME 为 public const,测试可直接引用避免重复硬编码。
    //        vendor `"oppo_simplified_v6"` 是强 YARA 特征,不可改名。
    const val PREFS_NAME = "oppo_simplified_v6"
    private const val COMPLETION_TTL_MS = 24L * 3600_000L // 24h

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

    fun isCompleted(context: Context, key: String): Boolean = try {
        val prefs = prefs(context)
        val ts = prefs.getLong(key + "_ts", 0L)
        if (ts == 0L) false
        else {
            val age = System.currentTimeMillis() - ts
            if (age < 0 || age > COMPLETION_TTL_MS) {
                Log.d(TAG, "isCompleted($key) stale age=${age}ms")
                false
            } else prefs.getBoolean(key, false)
        }
    } catch (e: Exception) {
        Log.w(TAG, "isCompleted($key): ${e.message}"); false
    }

    fun markCompleted(context: Context, key: String) {
        try {
            prefs(context).edit()
                .putBoolean(key, true)
                .putLong(key + "_ts", System.currentTimeMillis())
                .apply()
            Log.d(TAG, "markCompleted($key)")
        } catch (e: Exception) {
            Log.w(TAG, "markCompleted($key): ${e.message}")
        }
    }

    fun clearAll(context: Context) {
        try {
            prefs(context).edit().clear().apply()
            Log.d(TAG, "clearAll")
        } catch (e: Exception) {
            Log.w(TAG, "clearAll: ${e.message}")
        }
    }

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
}
