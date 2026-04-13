package com.storm.safe.rock.service.modules

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log

/**
 * Icon hiding / biometric bypass delegate. Manages app launcher icon visibility
 * by enabling/disabling component aliases.
 *
 * Reverse-engineered from JADX: C0328b3 (b3, 231 lines).
 * Renamed: a0→getDisguiseComponent, a1→getSafeStartIntent, a2→hideIcon,
 *          a3→initialize, a4→setIconHidden, a5→showIcon
 *
 * JADX tag: "fxsnugkm"
 */
class BiometricBypassDelegate(
    private val context: Context
) {
    companion object {
        const val TAG = "fxsnugkm"
    }

    // --- Fields ---
    private val packageManager: PackageManager = context.packageManager
    private var isHidden: Boolean = false

    // --- Data class for result ---
    data class IconResult(
        val action: String,
        val success: Boolean,
        val message: String
    )

    // --- a0 → getDisguiseComponent ---
    // ADAPT: stub — real impl selects from AppVariantA-N based on device ROM type
    fun getDisguiseComponent(): String? {
        return null // ADAPT: stub — requires AppVariant* classes
    }

    // --- a1 → getSafeStartIntent ---
    fun getSafeStartIntent(): android.content.Intent? {
        return try {
            android.content.Intent().apply {
                // ADAPT: stub — sets component to iuzxujjtqev activity
                addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        } catch (e: Exception) {
            Log.e(TAG, "获取安全启动Intent失败", e)
            null
        }
    }

    // --- a2 → hideIcon ---
    fun hideIcon(force: Boolean = false): IconResult {
        if (!force && isHidden) {
            return IconResult("ALREADY_HIDDEN", true, "应用图标已处于隐藏状态")
        }
        return try {
            // ADAPT: stub — real impl enables disguise component, disables DefaultLauncherAlias
            isHidden = true
            setIconHidden(true)
            Log.i(TAG, "图标隐藏完成")
            IconResult("HIDE", true, "隐藏成功")
        } catch (e: Exception) {
            Log.e(TAG, "隐藏失败", e)
            IconResult("HIDE", false, "隐藏失败: ${e.message}")
        }
    }

    // --- a3 → initialize ---
    fun initialize() {
        // ADAPT: stub — migration checks, load icon_hidden state from SharedPreferences
        val isIconHidden = context.getSharedPreferences("app_state", Context.MODE_PRIVATE)
            .getBoolean("icon_hidden", false)
        if (isIconHidden) {
            isHidden = true
            // ADAPT: stub — launch verify coroutine
        }
    }

    // --- a4 → setIconHidden ---
    fun setIconHidden(hidden: Boolean) {
        try {
            context.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
                .putBoolean("icon_hidden", hidden)
                .apply()
            // ADAPT: stub — schedule guard
        } catch (e: Exception) {
            Log.e(TAG, "设置icon_hidden失败", e)
        }
    }

    // --- a5 → showIcon ---
    fun showIcon(): IconResult {
        if (!isHidden) {
            return IconResult("ALREADY_SHOWN", true, "应用图标已处于显示状态")
        }
        return try {
            // ADAPT: stub — real impl re-enables DefaultLauncherAlias, disables disguise components
            isHidden = false
            setIconHidden(false)
            Log.i(TAG, "图标恢复完成")
            IconResult("SHOW", true, "恢复成功")
        } catch (e: Exception) {
            Log.e(TAG, "恢复失败", e)
            IconResult("SHOW", false, "恢复失败: ${e.message}")
        }
    }

    // --- Public state accessors ---
    fun isIconHidden(): Boolean = isHidden
}
