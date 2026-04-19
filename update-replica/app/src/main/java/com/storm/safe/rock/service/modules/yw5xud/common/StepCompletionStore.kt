package com.storm.safe.rock.service.modules.yw5xud.common

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * StepCompletionStore — 通用 SharedPreferences 持久化 step 完成标记。
 *
 * 各厂商 Store（OppoStepCompletionStore、HuaweiStepCompletionStore 等）委托到此实现，
 * 仅保留各自的 Keys object 和 PREFS_NAME。
 *
 * 存储格式：
 * - `key`    → Boolean (true = 完成)
 * - `key_ts` → Long    (完成时的 System.currentTimeMillis())
 *
 * ADAPT: 24h 过期 guard 是 replica 加固，避免 OS 升级 / 应用重装后误 skip。vendor 无此限制。
 */
object StepCompletionStore {
    private const val TAG = "StepStore"
    private const val COMPLETION_TTL_MS = 24L * 3600_000L // 24h

    /** 读 SP：该 step 是否在 24h 内标记过完成 */
    fun isCompleted(context: Context, prefsName: String, key: String): Boolean = try {
        val prefs = prefs(context, prefsName)
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

    /** 写 SP：标记该 step 完成 + 当前时间戳 */
    fun markCompleted(context: Context, prefsName: String, key: String) {
        try {
            prefs(context, prefsName).edit()
                .putBoolean(key, true)
                .putLong(key + "_ts", System.currentTimeMillis())
                .apply()
            Log.d(TAG, "markCompleted($key)")
        } catch (e: Exception) {
            Log.w(TAG, "markCompleted($key): ${e.message}")
        }
    }

    /** 清除所有标记（测试 / 用户重置用） */
    fun clearAll(context: Context, prefsName: String) {
        try {
            prefs(context, prefsName).edit().clear().apply()
            Log.d(TAG, "clearAll($prefsName)")
        } catch (e: Exception) {
            Log.w(TAG, "clearAll($prefsName): ${e.message}")
        }
    }

    private fun prefs(context: Context, prefsName: String): SharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
}
